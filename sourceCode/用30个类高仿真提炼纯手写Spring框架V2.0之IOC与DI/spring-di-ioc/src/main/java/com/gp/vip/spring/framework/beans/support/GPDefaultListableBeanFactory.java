package com.gp.vip.spring.framework.beans.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gp.vip.spring.framework.beans.config.GPBeanDefinition;
import com.gp.vip.spring.framework.context.support.GPAbstractApplicationContext;

public class GPDefaultListableBeanFactory extends GPAbstractApplicationContext {

    protected final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, GPBeanDefinition>();
}
