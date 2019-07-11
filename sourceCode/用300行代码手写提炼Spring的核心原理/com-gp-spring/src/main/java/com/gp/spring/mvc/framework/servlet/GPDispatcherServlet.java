package com.gp.spring.mvc.framework.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.spring.mvc.annotation.GPAutoWrited;
import com.gp.spring.mvc.annotation.GPGetMapping;
import com.gp.spring.mvc.annotation.GPPostMapping;
import com.gp.spring.mvc.annotation.GPService;

public class GPDispatcherServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 553489557714406121L;

    Properties properties = new Properties();
    List<String> classFileList = new ArrayList<String>();
    Map<String, Object> ioc = new HashMap<String, Object>();
    List<MethodHandler> handlerMapping = new ArrayList<MethodHandler>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatcher(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Details:\r\n"
                + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatcher(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Details:\r\n"
                + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        MethodHandler methodHandler = getMethodHandler(req);
        if (null == methodHandler) {
            return;
        }

        Map<String, String[]> params = req.getParameterMap();
        Map<String, Integer> paramIndex = methodHandler.paramIndex;
        Object[] paramValues = new Object[params.size()];
        for (Entry<String, String[]> entry : params.entrySet()) {
            String paramValue = Arrays.toString(entry.getValue()).replaceAll("\\[\\]", "");

            Integer index = paramIndex.get(entry.getKey());
            paramValues[index] = paramValue;
        }

        // 设置方法中的request和response对象
        int reqIndex = paramIndex.get(HttpServletRequest.class.getName());
        paramValues[reqIndex] = req;
        int respIndex = paramIndex.get(HttpServletResponse.class.getName());
        paramValues[respIndex] = resp;

        methodHandler.method.invoke(methodHandler.controller, paramValues);
    }

    private MethodHandler getMethodHandler(HttpServletRequest req) {
        String url = req.getRequestURI();
        url = url.replaceAll(req.getContextPath(), "").replaceAll("/+", "/");
        for (MethodHandler methodHandler : handlerMapping) {
            if (methodHandler.url.equals(url)) {
                return methodHandler;
            }
        }

        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 1.加载配置文件
        try {
            // loadProperty(config.getInitParameter("contextConfigLocation"));

            // 扫描所有
            // doScanner(properties.getProperty("scanPackage"));
            doScanner("com.gp.spring.mvc");

            // 3、初始化所有相关类的实例，并保存到IOC容器中
            doInstance();

            // //4、依赖注入
            doAutowired();
            //
            // 5、构造HandlerMapping
            initHandlerMapping();

        } catch (Exception e) {

        }
    }

    private void initHandlerMapping() {

        for (Entry<String, Object> entry : ioc.entrySet()) {
            Object obj = entry.getValue();
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                try {
                    method.setAccessible(true);
                    String url = null;
                    if (method.isAnnotationPresent(GPGetMapping.class)) {
                        url = method.getAnnotation(GPGetMapping.class).name();
                    } else if (method.isAnnotationPresent(GPPostMapping.class)) {
                        url = method.getAnnotation(GPPostMapping.class).name();
                    }
                    Parameter[] params = method.getParameters();
                    Map<String, Integer> paramNames = new HashMap<String, Integer>();
                    for (int i = 0; i < params.length; i++) {
                        Parameter param = params[i];
                        paramNames.put(param.getName(), new Integer(i));
                    }
                    handlerMapping.add(new MethodHandler(paramNames, method, obj, url));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void doAutowired() {
        for (Entry<String, Object> entry : ioc.entrySet()) {
            Object obj = entry.getValue();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(GPAutoWrited.class)) {
                        Object autoWritedObj = ioc.get(field.getName());
                        field.set(obj, autoWritedObj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void doInstance() throws Exception {

        for (String className : classFileList) {
            Class<?> clazz = Class.forName(className);
            className = className.substring(className.lastIndexOf(".") + 1, className.length());
            if (clazz.isAnnotationPresent(GPService.class)) {
                GPService service = clazz.getAnnotation(GPService.class);
                className = service.name();
            }

            ioc.put(className, Class.forName(className).newInstance());
        }
    }

    private void doScanner(String classPath) {

        classPath = classPath.replaceAll("\\.", "\\");
        File file = new File(classPath);
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                doScanner(classPath + "\\" + f.getName());
            }

            classFileList.add(f.getPath() + "." + f.getName().replace(".class", ""));
        }
    }

    private void loadProperty(String initParameter) throws IOException {

        InputStream is = this.getClass().getClassLoader().getResource(initParameter).openStream();

        properties.load(is);
    }

    public class MethodHandler {

        private Map<String, Integer> paramIndex;

        private Method method;

        private Object controller;

        private String url;

        public MethodHandler(Map<String, Integer> paramIndex, Method method, Object controller, String url) {
            this.paramIndex = paramIndex;
            this.method = method;
            this.controller = controller;
            this.url = url;
        }

        public Map<String, Integer> getParamIndex() {
            return paramIndex;
        }

        public void setParamIndex(Map<String, Integer> paramIndex) {
            this.paramIndex = paramIndex;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}
