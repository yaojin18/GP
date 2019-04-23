package cn.com.gp.pattern.adapter;

public class NewSystemService extends OldSystemService {

	public void loginForWeChat() {
		System.out.println("这是微信登录");
	}
}
