package cn.com.gp.singleton.register;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RegisterSingletonTest {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Executors.newScheduledThreadPool(10).execute(new Runnable() {
            @Override
            public void run() {
                try {
                   Singleton singleton = (Singleton) RegisterSingleton.getInstance(Singleton.class);
                    System.out.println(singleton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
