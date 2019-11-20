package com.example.demo.start.autoconfiguration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = DataConfigurationProperties.prefix)
public class DataConfigurationProperties {

	public static final String prefix = "com.example.demo.data";

	private Map<String, String> data;

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
