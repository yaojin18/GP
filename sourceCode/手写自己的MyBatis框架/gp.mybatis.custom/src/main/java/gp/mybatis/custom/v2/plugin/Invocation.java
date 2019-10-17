 package gp.mybatis.custom.v2.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Data;

@Data
public class Invocation {
     private Object target;
     private Method method;
     private Object[] args;
     
    public Invocation(Object target, Method method, Object[] args) {
        super();
        this.target = target;
        this.method = method;
        this.args = args;
    }
     
     public Object proceed() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
         return method.invoke(target, args);
     }

}
