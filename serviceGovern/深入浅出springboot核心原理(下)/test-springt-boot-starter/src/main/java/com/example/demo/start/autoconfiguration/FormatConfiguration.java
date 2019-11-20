package com.example.demo.start.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.demo.start.format.Format;
import com.example.demo.start.format.JsonFormat;
import com.example.demo.start.format.StringFormat;

@Configuration
public class FormatConfiguration {

	@ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
	@Bean
	public Format jsonFormat() {
		return new JsonFormat();
	}

	@Primary
	@ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
	@Bean
	public Format stringFormat() {
		return new StringFormat();
	}

}
