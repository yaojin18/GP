 package gp.mybatis.custom.v2.executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import gp.mybatis.custom.v2.parameter.ParameterHandler;
import gp.mybatis.custom.v2.session.Configuration;


public class StatementHandler {
     
     private ResultSetHandler resultSetHandler = new ResultSetHandler();
     
     public <T> T query(String statement, Object[] parameter, Class pojo) {
         Connection conn = null;
         PreparedStatement ps = null;
         Object result = null;
         try {
            conn = getConnection();
            ps = conn.prepareStatement(statement);
            ParameterHandler ph = new ParameterHandler(ps); 
            ph.setParameters(parameter);
            ps.execute();
            try {
                result = resultSetHandler.handle(ps.getResultSet(), pojo);
            } catch (Exception e) {
                // TODO: handle exception
            }
            return (T)result;
        } catch (Exception e) {
            // TODO: handle exception
        }
         
         return null;
     }

     /**
      * 获取连接
      * @return
      * @throws SQLException
      */
     private Connection getConnection() {
         String driver = Configuration.properties.getString("jdbc.driver");
         String url =  Configuration.properties.getString("jdbc.url");
         String username = Configuration.properties.getString("jdbc.username");
         String password = Configuration.properties.getString("jdbc.password");
         Connection conn = null;
         try {
             Class.forName(driver);
             conn = DriverManager.getConnection(url, username, password);
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return conn;
     }
}
