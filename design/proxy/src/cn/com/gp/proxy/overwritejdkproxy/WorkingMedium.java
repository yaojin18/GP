package cn.com.gp.proxy.overwritejdkproxy;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WorkingMedium implements GPInvocationHandler {

    private Object proxy;

    public Object getInstance(Object proxy) throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        this.proxy = proxy;
        return  GPProxy.newProxyInstance(new GPClassLoader(), proxy.getClass().getInterfaces(), this);
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
