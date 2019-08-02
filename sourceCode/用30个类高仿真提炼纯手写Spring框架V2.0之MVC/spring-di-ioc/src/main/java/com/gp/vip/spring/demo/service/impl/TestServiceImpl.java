package com.gp.vip.spring.demo.service.impl;

import com.gp.vip.spring.demo.service.TestService;
import com.gp.vip.spring.framework.annotation.GPService;

@GPService
public class TestServiceImpl implements TestService {

    public String findUser(String name) {
        return "hello " + name;
    }

}
