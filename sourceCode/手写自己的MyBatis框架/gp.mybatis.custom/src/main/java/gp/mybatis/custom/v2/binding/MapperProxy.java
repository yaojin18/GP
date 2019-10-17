package gp.mybatis.custom.v2.binding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import gp.mybatis.custom.v2.session.DefaultSqlSession;

/**
 * MapperProxy代理类，用于代理Mapper接口
 * 
 * @author YAOJIN18
 * @date 2019/10/14
 */
public class MapperProxy implements InvocationHandler {

    private DefaultSqlSession sqlSession;
    private Class object;

    public MapperProxy(DefaultSqlSession sqlSession, Class object) {
        super();
        this.sqlSession = sqlSession;
        this.object = object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = mapperInterface + "." + methodName;
        if (sqlSession.getConfiguration().hasStatement(statementId)) {
            return sqlSession.selectOne(statementId, args, object);
        }

        return method.invoke(proxy, args);
    }

}
