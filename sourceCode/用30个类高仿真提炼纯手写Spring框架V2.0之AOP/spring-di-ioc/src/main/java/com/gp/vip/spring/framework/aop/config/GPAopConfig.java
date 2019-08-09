 package com.gp.vip.spring.framework.aop.config;

import lombok.Data;

@Data
 public class GPAopConfig {
     
     private String pointCut;
     private String aspectBefore;
     private String aspectAfter;
     private String aspectClass;
     private String aspectAfterThrow;
     private String aspectAfterThrowingName;
     
 }
