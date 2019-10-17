package gp.mybatis.custom.v2.interceptor;

import java.util.Arrays;

import gp.mybatis.custom.v2.annotation.Intercepts;
import gp.mybatis.custom.v2.plugin.Interceptor;
import gp.mybatis.custom.v2.plugin.Invocation;
import gp.mybatis.custom.v2.plugin.Plugin;

@Intercepts("query")
public class MyPlugin implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        String statement = (String)invocation.getArgs()[0];
        Object[] parameter = (Object[])invocation.getArgs()[1];
        Class pojo = (Class)invocation.getArgs()[2];
        System.out.println("插件输出：SQL：[" + statement + "]");
        System.out.println("插件输出：Parameters：" + Arrays.toString(parameter));
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
