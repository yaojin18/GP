package com.example.demo.start;

import com.example.demo.start.autoconfiguration.DataConfigurationProperties;
import com.example.demo.start.format.Format;

public class FormatTemplate {

	private Format format;
	private DataConfigurationProperties properties;

	public FormatTemplate(Format format, DataConfigurationProperties properties) {
		this.format = format;
		this.properties = properties;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public <T> String format(T type) {
		StringBuffer sb = new StringBuffer();

		sb.append(format.format(type)).append("data is:").append(format.format(properties.getData()));

		return sb.toString();
	}

}
