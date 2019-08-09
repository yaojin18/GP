 package com.gp.vip.spring.framework.aop.aspect;

import java.lang.reflect.Method;

public interface GPJoinPoint {

     Object getThis();
     
     Object getUserAttribute(String key);
     
     void setUserAttribute(String key, Object value);
     
     Object[] getArguments();
     
     Method getMethod();
}
