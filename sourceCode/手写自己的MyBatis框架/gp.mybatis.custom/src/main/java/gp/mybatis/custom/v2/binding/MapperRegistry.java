 package gp.mybatis.custom.v2.binding;

import java.util.HashMap;
import java.util.Map;

import gp.mybatis.custom.v2.session.DefaultSqlSession;

public class MapperRegistry {

     private final Map<Class<?>, MapperProxyFactory> knownMappers = new HashMap();
     
     /**
      * configuration中解析接口上的注解时，存入接口和工厂类的映射关系，该pojo用于结果处理
      * @param clazz
      * @param pojo
      */
     public <T> void addMapper(Class<T> clazz, Class pojo) {
         knownMappers.put(clazz, new MapperProxyFactory<T>(clazz, pojo));
     }
     
     public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
         MapperProxyFactory proxyFactory = knownMappers.get(clazz);
         if(proxyFactory == null) {
             throw new RuntimeException("Type: "+clazz+" can not find");
         }
         
         return (T)proxyFactory.newInstance(sqlSession);
     }
     
}
