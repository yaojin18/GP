 package com.gp.vip.rpc.client;

import java.io.IOException;

import com.gp.vip.rpc.api.StudentService;

public class Client {

     public static void main(String[] args) throws IOException {
        
         ProxyHandler ph = new ProxyHandler(new ServiceDiscoveryImpl());
         
         StudentService studentService = (StudentService)ph.getProxyObject(StudentService.class);
         String result = studentService.findStudent("yaojin");
         
         System.out.println(result);
         System.in.read();
    }
}
