 package com.gp.vip.spring.framework.aop;

 public interface GPAopProxy {

     Object getProxy();
     
     Object getProxy(ClassLoader clazzLoader);
}
