package com.gp.demo.dubbo.service;

import java.io.IOException;

import org.apache.dubbo.container.Main;

public class DemoMain {

	public static void main(String[] args) throws IOException {
		Main.main(new String[]{"spring","log4j"});
		System.in.read();
	}

}
