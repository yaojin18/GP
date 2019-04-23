package cn.com.gp.pattern.adapter;

public class AdapterTest {

	public static void main(String[] args) {
		
		new NewSystemService().loginForWeChat();
		
		new NewSystemService().loginForUser("yj", "dd");
		
	}
}
