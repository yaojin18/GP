package com.gp.vip.spring.framework.webmvc.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.vip.spring.framework.annotation.GPRequestMapping;
import com.gp.vip.spring.framework.context.GPApplicationContext;

public class GPDispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private GPApplicationContext context;

    private List<GPHandlerMapping> handlerMapping = new ArrayList<GPHandlerMapping>();

    private Map<GPHandlerMapping, GPHandlerAdapter> handlerAdapters = new HashMap<GPHandlerMapping, GPHandlerAdapter>();

    private List<GPViewResolver> viewResolvers = new ArrayList<GPViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            this.doDispatch(req, resp);
        } catch (Exception e) {
            // TODO: handle exception

            resp.getWriter().write("系统异常");
        }

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {

        GPHandlerMapping handler = getHandler(req);
    }

    private GPHandlerMapping getHandler(HttpServletRequest req) {
        if (this.handlerMapping.isEmpty()) {
            return null;
        }

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");

        for (GPHandlerMapping handler : this.handlerMapping) {
            try {

                Matcher matcher = handler.getPattern().matcher(url);
                if (matcher.matches()) {
                    return handler;
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 1、初始化ApplicationContext
        context = new GPApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));

        // 2、初始化MVC的9大組件
        initStrategies(context);

    }

    protected void initStrategies(GPApplicationContext context) {
        // 多文件上传组件
        initMultipartResolver(context);

        // 初始化本地语言环境
        initLocaleResolver(context);

        // 初始化模板处理器
        initThemeResolver(context);

        initHandlerMappings(context);
        
        //初始化参数适配器
        initHandlerAdapters(context);
        
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        
        //初始化视图转换器
        initViewResolvers(context);
        
        //参数缓存器
        initFlashMapManager(context);
    }

    private void initFlashMapManager(GPApplicationContext context2) {
        // TODO Auto-generated method stub
         
    }

    private void initViewResolvers(GPApplicationContext context2) {

        String templateRoot = context2.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        
        File templateRootDir = new File(templateRootPath);
        String[] templates = templateRootDir.list();
        for(int i = 0; i < templates.length; i++) {
            this.viewResolvers.add(new GPViewResolver(templateRoot));
        }
    }

    private void initRequestToViewNameTranslator(GPApplicationContext context2) {
        // TODO Auto-generated method stub
         
    }

    private void initHandlerExceptionResolvers(GPApplicationContext context2) {
        // TODO Auto-generated method stub
         
    }

    private void initHandlerAdapters(GPApplicationContext context2) {
        // TODO Auto-generated method stub
        //把一个request请求变成一个handler，参数都是字符串的，自动配到handler中的形参
        
        for(GPHandlerMapping handlerMapping : this.handlerMapping) {
            
            this.handlerAdapters.put(handlerMapping, new GPHandlerAdapter());
        }
        
        
    }

    private void initHandlerMappings(GPApplicationContext context2) {

        String[] beanNames = context.getBeanDefinitionNames();

        try {
            for (String beanName : beanNames) {
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                
                String baseUrl = "";
                if(clazz.isAnnotationPresent(GPRequestMapping.class)) {
                    GPRequestMapping requestMapping =  clazz.getAnnotation(GPRequestMapping.class);
                    baseUrl = requestMapping.value();
                }
                
                for(Method method : clazz.getMethods()) {
                    if(method.isAnnotationPresent(GPRequestMapping.class)) {
                        GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                        String regex = ("/"+baseUrl+"/"+requestMapping.value().replaceAll("\\*", ".*"))
                            .replaceAll("/+", "/");
                        
                        Pattern pattern = Pattern.compile(regex);
                        
                        this.handlerMapping.add(new GPHandlerMapping(pattern, controller, method));
                    }
                }
                
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void initThemeResolver(GPApplicationContext context2) {
        // TODO Auto-generated method stub

    }

    private void initLocaleResolver(GPApplicationContext context2) {
        // TODO Auto-generated method stub

    }

    private void initMultipartResolver(GPApplicationContext context2) {
        // TODO Auto-generated method stub

    }

}
