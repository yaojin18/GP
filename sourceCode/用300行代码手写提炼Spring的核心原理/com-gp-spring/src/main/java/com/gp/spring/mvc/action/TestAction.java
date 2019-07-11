 package com.gp.spring.mvc.action;

import com.gp.spring.mvc.annotation.GPAutoWrited;
import com.gp.spring.mvc.annotation.GPController;
import com.gp.spring.mvc.annotation.GPGetMapping;
import com.gp.spring.mvc.service.TestService;

@GPController
 public class TestAction {

    @GPAutoWrited
    private TestService testService;
    
    @GPGetMapping("/test/findUser")
    public String testFindUser(String name) {
        return testService.testFindUser(name);
    }
    
}
