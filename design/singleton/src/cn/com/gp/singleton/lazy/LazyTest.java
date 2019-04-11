package cn.com.gp.singleton.lazy;

public class LazyTest {

    public static void main(String[] args) {

        new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println(Lazy.getInstance());
                Lazy.getInstance().test();
            }
        }).start();

        new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println(Lazy.getInstance());
                Lazy.getInstance().test();
            }
        }).start();
    }
}
