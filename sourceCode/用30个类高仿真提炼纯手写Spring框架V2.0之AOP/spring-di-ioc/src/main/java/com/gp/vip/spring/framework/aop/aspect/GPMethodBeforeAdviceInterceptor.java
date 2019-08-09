package com.gp.vip.spring.framework.aop.aspect;

import java.lang.reflect.Method;

import com.gp.vip.spring.framework.aop.intercept.GPMethodInterceptor;
import com.gp.vip.spring.framework.aop.intercept.GPMethodInvocation;

public class GPMethodBeforeAdviceInterceptor extends GPAbstractAspectAdvice implements GPAdvice, GPMethodInterceptor {

    private GPJoinPoint joinPoint;

    public GPMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method, Object[] args, Object target) throws Throwable {
        super.invokeAdviceMethod(joinPoint, null, null);
    }

    public Object invoke(GPMethodInvocation mi) throws Throwable {

        this.joinPoint = mi;
        before(mi.getMethod(), mi.getArguments(), mi.getThis());

        return mi.proceed();
    }

}
