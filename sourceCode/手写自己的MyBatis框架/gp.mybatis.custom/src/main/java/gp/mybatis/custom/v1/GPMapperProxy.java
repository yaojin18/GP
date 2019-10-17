 package gp.mybatis.custom.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GPMapperProxy implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
         return null;
    }
//
//    private GPSqlSession sqlSession;
//    
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        // TODO Auto-generated method stub
//        String mapperInterface = method.getDeclaringClass().getName();
//        String methodName = method.getName();
//        String statementId = mapperInterface+"."+methodName;
//        
//         return sqlSession.selectOne(statementId, args[0]);
//    }

}
