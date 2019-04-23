package cn.com.gp.proxy.testjdkproxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JobSeekerTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       Object proxy = new WorkingMedium().getInstance(new JobSeeker());
        Method method = proxy.getClass().getDeclaredMethod("findJob", null);
        method.invoke(proxy,null);
    }
}
