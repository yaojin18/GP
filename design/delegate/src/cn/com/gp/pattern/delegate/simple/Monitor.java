package cn.com.gp.pattern.delegate.simple;

import java.util.HashMap;
import java.util.Map;

public class Monitor {
	
	private Map<String, Student> targetStudentMap = new HashMap<String, Student>();
	
	public Monitor() {
		targetStudentMap.put("数学", new StudentB());
		targetStudentMap.put("语文", new StudentA());
	}
	
	public void doing(String typeName) {
		targetStudentMap.get(typeName).collectHomeWork(typeName);
	}

}
