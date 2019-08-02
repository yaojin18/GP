package com.gp.vip.spring.framework.context;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.gp.vip.spring.framework.annotation.GPAutowired;
import com.gp.vip.spring.framework.annotation.GPController;
import com.gp.vip.spring.framework.annotation.GPService;
import com.gp.vip.spring.framework.beans.GPBeanWrapper;
import com.gp.vip.spring.framework.beans.config.GPBeanDefinition;
import com.gp.vip.spring.framework.beans.config.GPBeanPostProcessor;
import com.gp.vip.spring.framework.beans.support.GPBeanDefinitionReader;
import com.gp.vip.spring.framework.beans.support.GPDefaultListableBeanFactory;
import com.gp.vip.spring.framework.core.GPBeanFactory;

public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String[] configLoactions;

    private GPBeanDefinitionReader reader;

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    private Map<String, GPBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, GPBeanWrapper>();

    public GPApplicationContext(String... configLoactions) {
        this.configLoactions = configLoactions;

        try {
            refresh();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void refresh() throws Exception {
        // 1、定位配置文件
        reader = new GPBeanDefinitionReader(this.configLoactions);

        // 将扫描的类封装成BeanDefinition
        List<GPBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        // 将配置信息放到容器中（伪IOC容器）
        doRegisterBeanDefinition(beanDefinitions);

        // 将非延迟加载的对象实例化
        doAutowired();

    }

    private void doAutowired() {

        for (Entry<String, GPBeanDefinition> entry : super.beanDefinitionMap.entrySet()) {

            String beanName = entry.getKey();
            if (!entry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        }
    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> beanDefinitions) throws Exception {
        // TODO Auto-generated method stub
        for (GPBeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The" + beanDefinition.getBeanClassName() + "is exists !");
            }

            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }

    }

    /**
     * 依赖注入，从这里开始，通过读取BeanDefinition中的信息 然后通过反射机制创建一个实例并返回 spring是用一个BeanWrapper进行一次包装，装饰器模式： 保留了原来的OOP关系
     * 可以进行扩展，增强（为以后的AOP打基础）
     */
    public Object getBean(String beanName) throws Exception {
        // TODO Auto-generated method stub

        GPBeanDefinition gpBeanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;

        // 工厂模式+策略模式
        GPBeanPostProcessor postProcessor = new GPBeanPostProcessor();

        postProcessor.postProcessBeforeInitialization(instance, beanName);

        instance = instantiateBean(beanName, gpBeanDefinition);

        // 将对象封装到BeanWrapper中
        GPBeanWrapper beanWrapper = new GPBeanWrapper(instance);

        this.factoryBeanInstanceCache.put(beanName, beanWrapper);

        postProcessor.postProcessAfterInitialization(instance, beanName);

        // 注入
        populateBean(beanName, new GPBeanDefinition(), beanWrapper);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, GPBeanDefinition gpBeanDefinition, GPBeanWrapper beanWrapper) {
        // TODO Auto-generated method stub
        Object instance = beanWrapper.getWrappedInstance();
        Class<?> clazz = beanWrapper.getWrappedClass();

        // 只有增加了注解的类才执行依赖注入
        if (!(clazz.isAnnotationPresent(GPController.class) || clazz.isAnnotationPresent(GPService.class))) {
            return;
        }

        // 获取所有的字段
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(GPAutowired.class)) {
                continue;
            }

            GPAutowired autowired = field.getDeclaredAnnotation(GPAutowired.class);
            String wiredName = autowired.value();
            if ("".equals(wiredName)) {
                wiredName = field.getType().getName();
            }

            field.setAccessible(true);

            // 延迟加载的实体未实例化
            if (this.factoryBeanInstanceCache.get(wiredName) == null) {
                continue;
            }

            try {
                field.set(instance, this.factoryBeanInstanceCache.get(wiredName));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private Object instantiateBean(String beanName, GPBeanDefinition gpBeanDefinition) {
        // TODO Auto-generated method stub
        String className = gpBeanDefinition.getBeanClassName();

        Object instance = null;
        try {

            if (this.singletonObjects.containsKey(className)) {
                instance = this.singletonObjects.get(className);
            } else {
                instance = Class.forName(className).newInstance();
                this.singletonObjects.put(className, instance);
                this.singletonObjects.put(gpBeanDefinition.getFactoryBeanName(), instance);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return instance;
    }

    public Object getBean(Class<?> beanClass) throws Exception {
        // TODO Auto-generated method stub
        return getBean(beanClass.getName());
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
       return this.reader.getConfig();
    }
    
}
