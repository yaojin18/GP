 package com.gp.vip.spring.framework.core;

 public interface GPBeanFactory {
     
     /**
      * 根据beanName从ioc容器中获取一个实例Bean
      * @param beanName
      * @return
      * @throws Exception
      */
     Object getBean(String beanName) throws Exception;

     
     public Object getBean(Class<?> beanClass) throws Exception;
}
