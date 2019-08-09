package com.gp.vip.spring.framework.aop.aspect;

import java.lang.reflect.Method;

import com.gp.vip.spring.framework.aop.intercept.GPMethodInterceptor;
import com.gp.vip.spring.framework.aop.intercept.GPMethodInvocation;

public class GPAfterReturningAdviceInterceptor extends GPAbstractAspectAdvice implements GPAdvice, GPMethodInterceptor {

    private GPJoinPoint joinPoint;

    public GPAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public Object invoke(GPMethodInvocation mi) throws Throwable {

        Object retVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object this1) throws Throwable {

        super.invokeAdviceMethod(this.joinPoint, retVal, null);
    }

}
