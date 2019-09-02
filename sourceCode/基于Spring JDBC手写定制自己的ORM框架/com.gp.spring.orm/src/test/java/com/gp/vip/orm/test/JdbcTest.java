 package com.gp.vip.orm.test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import com.gp.vip.orm.demo.entity.Member;

public class JdbcTest {

     public static void main(String[] args) {
        Member condition = new Member();
        condition.setName("Tom");
        condition.setAge(19);
        List<?> result = select(condition);
        System.out.println(Arrays.toString(result.toArray()));
     }

    private static List<?> select(Object condition) {
        List<Object> result = new ArrayList<Object>();
        Class<?> entityClass = condition.getClass();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("");
            Map<String,String> columnMapper = new HashMap<String,String>();
            Map<String,String> fieldMapper = new HashMap<String,String>();
            Field[] fields = entityClass.getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if(field.isAnnotationPresent(Column.class)) {
                    Column column = field.getDeclaredAnnotation(Column.class);
                    String columnName = column.name();
                    columnMapper.put(columnName, fieldName);
                    fieldMapper.put(fieldName, columnName);
                } else {
                    columnMapper.put(fieldName, fieldName);
                    fieldMapper.put(fieldName, fieldName);
                }
            }

            //创建语句集
            Table table = entityClass.getAnnotation(Table.class);
            String sql = "select * from "+table.name();
            
            StringBuffer where = new StringBuffer();
            for(Field field : fields) {
                Object value = field.get(condition);
                if(null != value) {
                    if(String.class == field.getType()) {
                        where.append(" and "+fieldMapper.get(field.getName())+" = '"+value+"'");
                    }else {
                        where.append(" and "+fieldMapper.get(field.getName())+" = "+value+"");
                    }
                }
            }
            
            pstm = con.prepareStatement(sql+where.toString());
            
            rs = pstm.executeQuery();
            
            int columnCounts = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                Object instance = entityClass.newInstance();
                for(int i = 1; i <= columnCounts; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Field field = entityClass.getDeclaredField(columnMapper.get(columnName));
                    field.setAccessible(true);
                    field.set(instance, rs.getObject(columnName));
                }
                
                result.add(instance);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }finally {
            try {
                rs.close();
                pstm.close();
                con.close();
            } catch (Exception e2) {
                // TODO: handle exception
            }
        }
        
        return result;
    }
}
