package com.gp.vip.rpc.server;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.gp.vip.rpc.server.impl.SpringConfig;

public class Bootstrap {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		((AnnotationConfigApplicationContext) context).start();
		System.in.read();

	}

}
