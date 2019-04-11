package cn.com.gp.singleton.register;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegisterSingleton {

   public static Map<String, Object> ioc = new ConcurrentHashMap<String, Object>();

   public static Object getInstance(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
       if(ioc.containsKey(clazz.getSimpleName())){
          return ioc.get(clazz.getSimpleName());
       }else {
           Constructor constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
           Object obj = constructor.newInstance();
           ioc.put(clazz.getSimpleName(),obj );
           return obj;
       }
   }

//    public static Object getInstance(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        if(ioc.containsKey(className)){
//            return ioc.get(className);
//        }else {
//            Class<Object> clazz = Class.forName(className);
//            Constructor<Object> c = clazz.getDeclaredConstructor(null);
//            c.setAccessible(true);
//            Object o = c.newInstance();
//            ioc.put(className, o);
//            return o;
//        }
//    }

}
