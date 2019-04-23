package cn.com.gp.proxy.testjdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WorkingMedium implements InvocationHandler {

    private Object proxy;

    public Object getInstance(Object proxy){
        this.proxy = proxy;
        return  Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        before();

        Object obj = method.invoke(this.proxy, args);

        after();

        return obj;
    }

    public void before(){
        System.out.println("should prepare resume before find job");
    }

    public void after(){
        System.out.println("good luck for you");
    }
}
