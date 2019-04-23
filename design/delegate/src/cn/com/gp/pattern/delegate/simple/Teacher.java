package cn.com.gp.pattern.delegate.simple;

public class Teacher {

	public void homeWork(String workName, Monitor monitor) {
		monitor.doing(workName);
	}
}
