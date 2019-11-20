package com.example.demo.start.format;

import java.util.Objects;

public class StringFormat implements Format {

	@Override
	public <T> String format(T type) {
		// TODO Auto-generated method stub
		return "StringFormat:" + Objects.toString(type);
	}

}
