 package com.gp.vip.rpc.server.impl;

import com.gp.vip.rpc.api.StudentService;
import com.gp.vip.rpc.server.RpcService;

@RpcService(StudentService.class)
public class StudentServiceImpl implements StudentService {

    public String findStudent(String userName) {
       return "hello "+userName;
    }

}
