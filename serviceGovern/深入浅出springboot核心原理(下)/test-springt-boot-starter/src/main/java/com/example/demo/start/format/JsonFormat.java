package com.example.demo.start.format;

import com.alibaba.fastjson.JSON;

public class JsonFormat implements Format {

	@Override
	public <T> String format(T type) {
		// TODO Auto-generated method stub
		return "JsonFormat:"+JSON.toJSONString(type);
	}


}
