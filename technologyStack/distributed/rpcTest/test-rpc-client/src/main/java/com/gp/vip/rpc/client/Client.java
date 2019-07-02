 package com.gp.vip.rpc.client;

import com.gp.vip.rpc.api.StudentService;

public class Client {

     public static void main(String[] args) {
        
         ProxyHandler ph = new ProxyHandler();
         
         StudentService studentService = (StudentService)ph.getProxyObject(StudentService.class);
         String result = studentService.findStudent("yaojin");
         
         System.out.println(result);
         
    }
}
