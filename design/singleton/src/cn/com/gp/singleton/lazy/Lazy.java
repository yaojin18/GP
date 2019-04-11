package cn.com.gp.singleton.lazy;

public class Lazy {
    private static Lazy instance = null;

    private Lazy(){

    }

    public static Lazy getInstance(){
        if(null == instance){
            synchronized (instance){
                if(null == instance){
                    instance = new Lazy();
                    return instance;
                }
            }
        }

        return instance;
    }

    public void test(){
        System.out.println("this is lazy class test method");
    }
}
