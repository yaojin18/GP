package gp.mybatis.custom.v2.session;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import gp.mybatis.custom.v2.TestMybatis;
import gp.mybatis.custom.v2.annotation.Entity;
import gp.mybatis.custom.v2.annotation.Select;
import gp.mybatis.custom.v2.binding.MapperRegistry;
import gp.mybatis.custom.v2.executor.CachingExecutor;
import gp.mybatis.custom.v2.executor.Executor;
import gp.mybatis.custom.v2.executor.SimpleExecutor;
import gp.mybatis.custom.v2.plugin.Interceptor;
import gp.mybatis.custom.v2.plugin.InterceptorChain;

public class Configuration {
    public static final ResourceBundle sqlMappings;// SQL映射关系配置，使用注解不用重复配置
    public static final ResourceBundle properties;// 全局配置
    public static final MapperRegistry MAPPER_REGISTRY = new MapperRegistry();// 维护接口与工厂类关系
    public static final Map<String, String> mappedStatements = new HashMap();// 维护接口方法与SQL关系

    private InterceptorChain interceptorChain = new InterceptorChain();// 插件
    private List<Class<?>> mapperList = new ArrayList();// 所有Mapper接口
    private List<String> classPaths = new ArrayList<String>();// 类所有文件

    static {
        sqlMappings = ResourceBundle.getBundle("sql");
        properties = ResourceBundle.getBundle("mybatis");
    }

    public Configuration() {
        for (String key : sqlMappings.keySet()) {
            Class mapper = null;
            String statement = null;
            String pojoStr = null;
            Class pojo = null;
            // properties中的value用--隔开，第一个是SQL语句
            statement = sqlMappings.getString(key).split("--")[0];
            pojoStr = sqlMappings.getString(key).split("--")[1];
            try {
                // properties中的key是接口类型+方法
                mapper = Class.forName(key.substring(0, key.lastIndexOf(".")));
                pojo = Class.forName(pojoStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 接口与返回的实体类关系
            MAPPER_REGISTRY.addMapper(mapper, pojo);
            mappedStatements.put(key, statement);// 接口方法与SQL关系
        }

        // 解析Mapper接口配置，扫描注册
        String mapperPath = properties.getString("mapper.path");
        scanPackage(mapperPath);
        for (Class<?> mapper : mapperList) {
            parsingClass(mapper);
        }

        // 解析插件
        String pluginPathValue = properties.getString("plugin.path");
        String[] pluginPaths = pluginPathValue.split(",");
        if (null != pluginPaths) {
            for (String plugin : pluginPaths) {
                Interceptor interceptor = null;
                try {
                    interceptor = (Interceptor)Class.forName(plugin).newInstance();
                } catch (Exception e) {
                    // TODO: handle exception
                }

                interceptorChain.addInterceptor(interceptor);
            }
        }
    }
    
    /**
     * 根据statement判断是否存在映射的sql
     * @param statementName
     * @return
     */
    public boolean hasStatement(String statementName) {
        return mappedStatements.containsKey(statementName);
    }
    
    /**
     * 根据statement获取sql
     * @param id
     * @return
     */
    public String getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
        return MAPPER_REGISTRY.getMapper(clazz, sqlSession);
    }
    
    /**
     * 创建执行器，当开启缓存时使用缓存执行器，当配置插件时，使用插件代理
     * @return
     */
    public Executor newExecutor() {
        Executor executor = null;
        if(properties.getString("cache.enabled").equals("true")) {
            executor = new CachingExecutor(new SimpleExecutor());
        }else {
            executor = new SimpleExecutor();
        }
        
        //目前只拦截了Executor，所有的插件都对Executor进行代理，没有对拦截类和方法名进行判断
        if(interceptorChain.hasPlugin()) {
            return (Executor)interceptorChain.pluginAll(executor);
        }
        
        return executor;
    }
    
    /**
     * 解析Mapper接口上的配置的注解
     * 
     * @param mapper
     */
    private void parsingClass(Class<?> mapper) {
        // 解析类上的注解
        // 如果有@Entity注解，说明是查询数据库接口
        if (mapper.isAnnotationPresent(Entity.class)) {
            Entity entity = mapper.getAnnotation(Entity.class);
            MAPPER_REGISTRY.addMapper(mapper, entity.value());
        }

        // 解析方法上的注解
        for (Method method : mapper.getMethods()) {
            // 解析是否配置select注解
            if (method.isAnnotationPresent(Select.class)) {
                Select select = method.getAnnotation(Select.class);
                String statement = method.getDeclaringClass().getName() + "." + method.getName();
                mappedStatements.put(statement, select.value());
            }
        }
    }

    /**
     * 根据全局配置文件的Mapper接口路径，扫描所有接口
     * 
     * @param mapperPath
     */
    private void scanPackage(String mapperPath) {
        String classPath = TestMybatis.class.getResource("/").getPath();
        mapperPath = mapperPath.replace(".", File.separator);
        String mainPath = classPath + mapperPath;
        doPath(new File(mainPath));
        for (String className : classPaths) {
            className = className.replace(classPath.replace("/", "\\").replaceFirst("\\\\", ""), "").replace("\\", ".")
                .replace(".class", "");
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (Exception e) {
                // TODO: handle exception
            }
            if (clazz.isInterface()) {
                mapperList.add(clazz);
            }
        }
    }

    /**
     * 获取所有的类 .class
     * 
     * @param file
     */
    private void doPath(File file) {
        if (file.isDirectory()) {
            for (File f1 : file.listFiles()) {
                doPath(f1);
            }
        } else {
            if (file.getName().endsWith(".class")) {
                classPaths.add(file.getPath());
            }
        }
    }
}
