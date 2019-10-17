package gp.mybatis.custom.v2.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import gp.mybatis.custom.v2.annotation.Intercepts;

public class Plugin implements InvocationHandler {

    private Object target;
    private Interceptor interceptor;

    public Plugin(Object target, Interceptor interceptor) {
        super();
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 对被代理对象进行代理，返回代理类
     * 
     * @param obj
     * @param interceptor
     * @return
     */
    public static Object wrap(Object obj, Interceptor interceptor) {
        Class clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new Plugin(obj, interceptor));
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        // 自定义插件有Interceptor注解，指定拦截的方法
        if (interceptor.getClass().isAnnotationPresent(Intercepts.class)) {
            // 如果是被拦截的方法，则进入自定义拦截器
            if (method.getName().equals(interceptor.getClass().getAnnotation(Intercepts.class).value())) {
                return interceptor.intercept(new Invocation(target, method, args));
            }
        }

        // 非拦截方法，执行原逻辑
        return method.invoke(target, method, args);
    }

}
