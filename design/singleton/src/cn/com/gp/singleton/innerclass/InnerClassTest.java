package cn.com.gp.singleton.innerclass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InnerClassTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Class innerClazz = InnerClass.class;
        Constructor c =    innerClazz.getDeclaredConstructor(null);
        c.setAccessible(true);
        c.newInstance();

    }
}

