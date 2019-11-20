package com.example.demo.format.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.start.FormatTemplate;


@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private FormatTemplate formatTemplate;

	@GetMapping("/hello")
	public String hello() {
		User user = new User();
		user.setName("yj");
		user.setCode("123");
		return formatTemplate.format(user);
	}
}
