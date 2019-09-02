package javax.core.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    protected static final Log logger = LogFactory.getLog(BeanUtils.class);
    
    private BeanUtils() {
        
    }
    
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    private static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        if(index >= params.length || index < 0) {
            return Object.class;
        }
        if(!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class)params[index];
    }
    
    public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
        return getDeclaredField(object.getClass(), propertyName);
    }
    
    public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
        for(Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(propertyName);
            }catch (Exception e) {
                // TODO: handle exception
            }
        }
        throw new NoSuchFieldException("No such field: " + clazz.getName()
        + '.' + propertyName);
    }
    
    public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
        Field field = getDeclaredField(object, propertyName);
        
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        
        Object result = null;
        try {
            result = field.get(object);
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        field.setAccessible(accessible);
        return result;
    }
    
    public static void forceSetProperty(Object object, String propertyName, Object newValue) throws NoSuchFieldException {
        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(object, newValue);
        } catch (IllegalAccessException e) {
            logger.info("Error won't happen");
        }
        field.setAccessible(accessible);
    }
    
    public static Object invokePrivateMethod(Object object, String methodName, Object... params) {
        Class[] types = new Class[params.length];
        for(int i=0; i<params.length; i++) {
            types[i] = params[i].getClass();
        }
        
        Class clazz = object.getClass();
        Method method = null;
        for(Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                method = superClass.getDeclaredMethod(methodName, types);
                break;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        
        if(method == null) {
            throw new NoSuchMethodError("No Such Method:"+clazz.getSimpleName()+methodName);
        }
        
        boolean accessible = method.isAccessible();
        method.setAccessible(true);
        Object result = null;
        try {
            result = method.invoke(object, params);
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        method.setAccessible(accessible);
        return result;
    }
    
    public static List<Field> getFieldsByType(Object object, Class type){
        List<Field> list = new ArrayList<Field>();
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields) {
            if(field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
        }
        
        return list;
    }
    
    public static Class getPropertyType(Class type, String name) throws NoSuchFieldException {
        return getDeclaredField(type, name).getType();
    }
    
    public static String getGetterName(Class type, String fieldName) {
        if(type.getName().equals("boolean")) {
            return "is"+org.apache.commons.lang.StringUtils.capitalize(fieldName);
        }else {
            return "get"+org.apache.commons.lang.StringUtils.capitalize(fieldName);
        }
    }
    
    public static Method getGetterMethod(Class type, String fieldName) {
        try {
            return type.getMethod(getGetterName(type, fieldName));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
    
    public static Object invoke(String className, String methodName, Class[] argsClass, Object[] args) throws Exception{
        Class cl = Class.forName(className);
        Method method = cl.getMethod(methodName, argsClass);
        return method.invoke(cl.newInstance(), args);
    }
    
    public static Object invoke(Object oldObject, String methodName,
        Class[] argsClass, Object[] args) throws SecurityException,
        NoSuchMethodException, IllegalArgumentException,
        IllegalAccessException, InvocationTargetException {
    Class cl = oldObject.getClass();
    Method method = cl.getMethod(methodName, argsClass);
    return method.invoke(oldObject, args);
}

public static String[] getFieldsName(Class cl) throws Exception {
    Field[] fields = cl.getDeclaredFields();
    String[] fieldNames = new String[fields.length];
    for (int i = 0; i < fields.length; i++) {
        fieldNames[i] = fields[i].getName();
    }
    return fieldNames;
}

public static List<String> getAllFieldName(Class cl) {
    List<String> list = new ArrayList<String>();
    Field[] fields = cl.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
        Field field = fields[i];
        if (field.getName().equals("serialVersionUID")) {
            continue;
        }
        list.add(field.getName());
    }
    while (true) {
        cl = cl.getSuperclass();
        if (cl == Object.class) {
            break;
        }
        list.addAll(getAllFieldName(cl));
    }
    return list;
}

public static List<Method> getSetter(Class cl) {
    List<Method> list = new ArrayList<Method>();
    Method[] methods = cl.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
        Method method = methods[i];
        String methodName = method.getName();
        if (!methodName.startsWith("set")) {
            continue;
        }
        list.add(method);
    }
    while (true) {
        cl = cl.getSuperclass();
        if (cl == Object.class) {
            break;
        }
        list.addAll(getSetter(cl));
    }
    return list;
}

public static List<Method> getGetter(Class cl) {
    List<Method> list = new ArrayList<Method>();
    Method[] methods = cl.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
        Method method = methods[i];
        String methodName = method.getName();
        if (!methodName.startsWith("get") && !methodName.startsWith("is")) {
            continue;
        }
        list.add(method);
    }
    while (true) {
        cl = cl.getSuperclass();
        if (cl == Object.class) {
            break;
        }
        list.addAll(getGetter(cl));
    }
    return list;
}

/**
 * Returns the classname without the package. Example: If the input class is
 * java.lang.String than the return value is String.
 * 
 * @param cl
 *            The class to inspect
 * @return The classname
 */
public static String getClassNameWithoutPackage(Class cl) {
    String className = cl.getName();
    int pos = className.lastIndexOf('.') + 1;
    if (pos == -1)
        pos = 0;
    String name = className.substring(pos);
    return name;
}

/**
 * 把DTO对象转成字符串
 * @param obj DTO对象
 * @return 带属性名和值的字符串
 */
public static String beanToString(Object obj){
    return ToStringBuilder.reflectionToString(obj);
}
    
    
    
    
    
    
    
    
    
    
    
    
    
}
