package cn.com.gp.singleton.hungry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class HungryTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Hungry.getInstance().test();

        //测试饿汉式单例防止反射实例化
        Constructor<Hungry> declaredConstructor = Hungry.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        Hungry hungry = declaredConstructor.newInstance();
        hungry.test();
    }

}
