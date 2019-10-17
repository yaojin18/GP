 package gp.mybatis.custom.v2.binding;

import java.lang.reflect.Proxy;

import gp.mybatis.custom.v2.session.DefaultSqlSession;

public class MapperProxyFactory<T> {

     private Class<T> mapperInterface;
     
     private Class object;

    public MapperProxyFactory(Class<T> mapperInterface, Class object) {
        super();
        this.mapperInterface = mapperInterface;
        this.object = object;
    }
     
     public T newInstance(DefaultSqlSession sqlSession) {
         return (T)Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] {mapperInterface}, new MapperProxy(sqlSession, object));
     }
}
