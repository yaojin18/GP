package com.gp.demo.dubbo.custom;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gp.demo.dubbo.api.DemoService;

public class DemoMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "application.xml" });

		DemoService demoService = context.getBean(DemoService.class);
		System.out.println(demoService.sayHello("yj"));
	}

}
