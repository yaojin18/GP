package cn.com.gp.singleton.innerclass;

public class InnerClass {

    private InnerClass(){
        if (SingletonInnerClass.instance != null){
            throw new RuntimeException("this is a singleton class");
        }
    }

    public static InnerClass getInstance(){
        return SingletonInnerClass.instance;
    }

    public void test(){
        System.out.println("this is lazy class test method");
    }

    private static class SingletonInnerClass{
        public static InnerClass instance = new InnerClass();
    }
}
