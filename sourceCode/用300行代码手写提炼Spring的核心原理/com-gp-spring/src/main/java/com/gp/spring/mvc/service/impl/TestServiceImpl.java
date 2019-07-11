 package com.gp.spring.mvc.service.impl;

import com.gp.spring.mvc.service.TestService;

public class TestServiceImpl implements TestService {

    public String testFindUser(String name) {
        return "hello " + name;
    }

}
