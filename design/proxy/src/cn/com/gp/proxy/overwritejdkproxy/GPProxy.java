package cn.com.gp.proxy.overwritejdkproxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GPProxy {

    private static final String ln = "\r\n";

    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
    }

    public static Object newProxyInstance(GPClassLoader classLoader, Class<?>[] interfaces, GPInvocationHandler h) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //生成代碼
        String src = generateCode(interfaces);
        System.out.println(src);

        //输出到磁盘
      String filePath = GPProxy.class.getResource("").getPath();
        File f = new File(filePath+"$Proxy0.java");
        FileWriter fw = new FileWriter(f);
        fw.write(src);
        fw.flush();
        fw.close();

        //编译
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(f);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
        task.call();
        standardFileManager.close();

        //加载
        System.out.println("classLoader is "+classLoader);
        Class<?> proxyClass = classLoader.findClass("$Proxy0");
        System.out.println("proxy class is "+proxyClass);
        Constructor<?> constructor = proxyClass.getConstructor(GPInvocationHandler.class);
        f.delete();

        return constructor.newInstance(h);
    }

    private static String generateCode(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append("package cn.com.gp.proxy.overwritejdkproxy; ").append(ln);
        sb.append("import java.lang.reflect.* ;").append(ln);
        sb.append("import cn.com.gp.proxy.* ;").append(ln);
        Class interfaceClazz = interfaces[0];
        sb.append("public class $Proxy0 implements "+interfaceClazz.getName()+"{").append(ln);
        sb.append(ln);
        sb.append("private GPInvocationHandler h;").append(ln);
        sb.append("public $Proxy0(GPInvocationHandler h){").append(ln);
        sb.append("this.h = h;").append(ln).append("}").append(ln);
        for(Method  method : interfaceClazz.getMethods()){

            Class<?>[] params = method.getParameterTypes();

            StringBuffer paramNames = new StringBuffer();
            StringBuffer paramValues = new StringBuffer();
            StringBuffer paramClazzs = new StringBuffer();

            for(int i= 0; i < params.length; i++){
               Class clazz = params[i];
               String paramName = clazz.getSimpleName().toLowerCase();
               String type = clazz.getName();
               paramNames.append(type +" "+paramName);
               paramClazzs.append(type +".class");
               paramValues.append(paramName);
                if(i > 0 && i < params.length-1){
                    paramNames.append(",");
                    paramClazzs.append(",");
                    paramValues.append(",");
                }
            }

            sb.append("public").append(" ")
                    .append(method.getReturnType()).append(" ")
                    .append(method.getName()).append("(")
                    .append(paramNames.toString()).append("){" + ln);

            sb.append("try {" +ln);
            sb.append("Method m = " + interfaceClazz.getName()+".class.getMethod(\"")
                    .append(method.getName()+"\",new Class[]{"+paramClazzs.toString()+"});").append(ln);

            sb.append((hasReturnValue(method.getReturnType())?"return ":"")+ "this.h.invoke(this, m, new Object[]{"+paramValues+"});"+ln);
            sb.append("}catch(Throwable e){"+ln);
            sb.append("throw new UndeclaredThrowableException(e);"+ln);
            sb.append("}");
            sb.append(getReturnEmptyCode(method.getReturnType()));
            sb.append("}");
        }

    sb.append("}"+ln);
        return sb.toString();
    }

    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "return 0;";
        }else if(returnClass == void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static boolean hasReturnValue(Class<?> clazz){
        return clazz != void.class;
    }
}
