 package gp.mybatis.custom.v1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import gp.mybatis.custom.v1.mapper.Blog;


public class GPExecutor {

     public <T> T query(String sql, Object paramater) {
         Connection conn = null;
         Statement stmt = null;
         Blog blog = new Blog();
         
         try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp-mybatis", "root", "123456");
            stmt = conn.createStatement(); 
           ResultSet rs = stmt.executeQuery(String.format(sql, paramater));
            
           while(rs.next()) {
               Integer bid = rs.getInt("bid");
               String name = rs.getString("name");
               Integer authorId = rs.getInt("author_id");
//               blog.setBid(bid);
               blog.setName(name);
               blog.setAuthorId(authorId);
           }
           
           rs.close();
           stmt.close();
           conn.close();
           
        } catch (Exception e) {
            // TODO: handle exception
        }
         
         return (T)blog;
     }
}
