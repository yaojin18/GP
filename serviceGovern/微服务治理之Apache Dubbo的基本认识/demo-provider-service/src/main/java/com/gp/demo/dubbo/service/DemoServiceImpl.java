package com.gp.demo.dubbo.service;

import com.gp.demo.dubbo.api.DemoService;

public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		return "hello " + name;
	}

}
