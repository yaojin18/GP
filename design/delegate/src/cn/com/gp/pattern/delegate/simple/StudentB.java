package cn.com.gp.pattern.delegate.simple;

public class StudentB implements Student {

	@Override
	public void collectHomeWork(String work) {
		System.out.println("我是学生B,我负责收集"+work+"作业");
	}

}
