 package com.gp.vip.orm.demo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.core.common.jdbc.datasource.DynamicDataSource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.gp.vip.orm.demo.entity.Order;
import com.gp.vip.orm.framework.BaseDaoSupport;

@Repository
 public class OrderDao extends BaseDaoSupport<Order, Long> {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private SimpleDateFormat fullDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DynamicDataSource dataSource;
    
    @Override
    protected String getPKColumn() {
        // TODO Auto-generated method stub
         return "id";
    }

    @Override
//    @Resource(name="dynamicDataSource")
    protected void setDataSource(DataSource dataSource) {
        // TODO Auto-generated method stub
         this.dataSource = (DynamicDataSource)dataSource;
         this.setDataSourceReadOnly(dataSource);
         this.setDataSourceWrite(dataSource);
    }

    public boolean insertOne(Order order)throws Exception{
        Date date = null;
        
        if(order.getCreateTime() == null) {
            date = new Date();
            order.setCreateTime(date.getTime());
        }else {
            date = new Date(order.getCreateTime());
        }
        Integer dbRouter = Integer.valueOf(yearFormat.format(date));
        this.dataSource.getDataSourceEntry().set(dbRouter);
        order.setCreateTimeFmt(fullDataFormat.format(date));
        Long orderId = super.insertAndReturnId(order);
        order.setId(orderId);
        return orderId>0;
    }
    
    
}
