package com.gp.vip.spring.framework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import com.gp.vip.spring.framework.aop.intercept.GPMethodInterceptor;
import com.gp.vip.spring.framework.aop.intercept.GPMethodInvocation;
import com.gp.vip.spring.framework.aop.support.GPAdvisedSupport;

public class GPJdkDynamicAopProxy implements GPAopProxy, InvocationHandler {

    private GPAdvisedSupport advised;

    public GPJdkDynamicAopProxy(GPAdvisedSupport config) {
        this.advised = config;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers =
            this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());

        GPMethodInvocation invocation = new GPMethodInvocation(proxy, method, this.advised.getTarget(), args,
            interceptorsAndDynamicMethodMatchers, this.advised.getTargetClass());

        return invocation.proceed();
    }

    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    public Object getProxy(ClassLoader clazzLoader) {
        return Proxy.newProxyInstance(clazzLoader, this.advised.getTargetClass().getInterfaces(), this);
    }

}
