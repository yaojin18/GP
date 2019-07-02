 package com.gp.vip.rpc.server.impl;

import com.gp.vip.rpc.api.StudentService;

public class StudentServiceImpl implements StudentService {

    public String findStudent(String userName) {
       return "hello "+userName;
    }

}
