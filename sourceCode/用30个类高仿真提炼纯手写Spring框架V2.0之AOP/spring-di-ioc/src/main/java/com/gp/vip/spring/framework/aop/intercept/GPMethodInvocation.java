package com.gp.vip.spring.framework.aop.intercept;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gp.vip.spring.framework.aop.aspect.GPJoinPoint;

public class GPMethodInvocation implements GPJoinPoint {

    private Object proxy;
    private Method method;
    private Object target;
    private Object[] arguments;
    private List<Object> interceptorsAndDynamicMethodMatchers;
    private Class<?> targetClass;
    private Map<String, Object> userAttributes;
    private int currentInterceptorIndex = -1;// 定义一个索引，从-1开始记录当前拦截器执行的位置

    public GPMethodInvocation(Object proxy, Method method, Object target, Object[] arguments,
        List<Object> interceptorsAndDynamicMethodMatchers, Class<?> targetClass) {
        this.proxy = proxy;
        this.method = method;
        this.target = target;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
        this.targetClass = targetClass;
    }

    public Object proceed() throws Throwable {
        // 如果Interceptor执行完了，则执行joinPoint
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target, this.arguments);
        }

        Object interceptorOrInterceptionAdvice =
            this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);

        // 动态匹配joinPoint
        if (interceptorOrInterceptionAdvice instanceof GPMethodInterceptor) {
            GPMethodInterceptor mi = (GPMethodInterceptor)interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        } else {
            // 动态匹配失败时，略过当前Interceptor，调用下一个Interceptor
            return proceed();
        }
    }

    public Object getThis() {
        return this.target;
    }

    public Object getUserAttribute(String key) {
        return this.userAttributes != null ? this.getUserAttribute(key) : null;
    }

    public void setUserAttribute(String key, Object value) {

        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<String, Object>();
            }
            this.userAttributes.put(key, value);
        } else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    public Method getMethod() {
        return this.method;
    }

}
