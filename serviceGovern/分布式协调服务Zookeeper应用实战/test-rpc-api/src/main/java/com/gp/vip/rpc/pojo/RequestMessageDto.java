 package com.gp.vip.rpc.pojo;

import java.io.Serializable;

import lombok.Data;

/**
  * 组装请求报文
  * @author YAOJIN18
  * @date 2019/07/01
  */
 @Data
 public class RequestMessageDto implements Serializable{

     /**
     *
     */
    private static final long serialVersionUID = 1338279776586182129L;

    private String className;
     
     private String methodName;
     
     private Class[] paramType;
     
     private Object[] args;
     
     private Object response;
}
