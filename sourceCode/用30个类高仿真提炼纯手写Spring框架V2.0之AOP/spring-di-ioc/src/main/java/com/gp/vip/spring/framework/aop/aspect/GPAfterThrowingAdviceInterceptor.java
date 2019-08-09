 package com.gp.vip.spring.framework.aop.aspect;

import java.lang.reflect.Method;

import com.gp.vip.spring.framework.aop.intercept.GPMethodInterceptor;
import com.gp.vip.spring.framework.aop.intercept.GPMethodInvocation;

public class GPAfterThrowingAdviceInterceptor extends GPAbstractAspectAdvice implements GPAdvice, GPMethodInterceptor{

    private String throwingName;
    
    public GPAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public Object invoke(GPMethodInvocation mi) throws Throwable {

        try {
            return mi.proceed();
        } catch (Throwable e) {
            invokeAdviceMethod(mi, null, e.getCause());
            throw e;
        }
    }
    
    public void setThrowName(String throwName){
        this.throwingName = throwName;
    }

}
