package cn.com.gp.pattern.delegate.simple;

public class DelegateTest {

	
	public static void main(String[] args) {
		
		new Teacher().homeWork("语文", new Monitor());
	}
}
