 package gp.mybatis.custom.v2.plugin;

 /**
  * 拦截器接口
  * @author YAOJIN18
  * @date 2019/10/14
  */
 public interface Interceptor {

     Object intercept(Invocation invocation) throws Throwable;
     
     Object plugin(Object target);
}
