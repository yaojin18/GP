package com.example.demo.start.autoconfiguration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.demo.start.FormatTemplate;
import com.example.demo.start.format.Format;

@Import(FormatConfiguration.class)
@EnableConfigurationProperties(DataConfigurationProperties.class)
@Configuration
public class FormatTemplateConfiguration {

	@Bean
	public FormatTemplate formatTemplate(Format format, DataConfigurationProperties properties) {
		return new FormatTemplate(format, properties);
	}

}
