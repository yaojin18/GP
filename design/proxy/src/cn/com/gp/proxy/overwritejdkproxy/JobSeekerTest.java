package cn.com.gp.proxy.overwritejdkproxy;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JobSeekerTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, IOException, InstantiationException {
       Object proxy = new WorkingMedium().getInstance(new JobSeeker());
        Method method = proxy.getClass().getDeclaredMethod("findJob", new Class[]{});
        method.invoke(proxy,new Object[]{});
    }
}
