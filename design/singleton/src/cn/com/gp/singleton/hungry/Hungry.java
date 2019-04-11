package cn.com.gp.singleton.hungry;

public class Hungry {
    private static final Hungry instance = new Hungry();

    private Hungry(){
        if(null != instance){
            throw new RuntimeException("this is hungry singleton class");
        }
    }

    public static Hungry getInstance(){
        return instance;
    }

    public void test(){
        System.out.println("this is hungry class test method");
    }
}
