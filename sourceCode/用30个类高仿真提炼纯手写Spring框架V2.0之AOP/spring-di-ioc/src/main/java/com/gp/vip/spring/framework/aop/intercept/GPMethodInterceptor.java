 package com.gp.vip.spring.framework.aop.intercept;

 public interface GPMethodInterceptor {

     Object invoke(GPMethodInvocation invocation) throws Throwable;
}
