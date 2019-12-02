package com.gp.vip.rpc.server.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.gp.vip")
public class SpringConfig {

	@Bean
	public GPRpcService gpRpcService() {
		return new GPRpcService(8081);
	}

}
