package com.gp.vip.spring.framework.webmvc.servlet;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.vip.spring.framework.annotation.GPRequestParam;

public class GPHandlerAdapter {

    public boolean supports(Object handler) {
        return handler instanceof GPHandlerMapping;
    }

    GPModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        GPHandlerMapping handlerMapping = (GPHandlerMapping)handler;

        // 把方法的形参和request的实参按顺序进行一一对应
        Map<String, Integer> paramIndexMapping = new HashMap<String, Integer>();

        // 提取方法中注解的参数，一个参数可以有多个注解，一个方法有多个参数
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < pa.length; i++) {
            for (Annotation a : pa[i]) {
                if (a instanceof GPRequestParam) {
                    String paramName = ((GPRequestParam)a).value();
                    if (!"".equals(paramName.trim())) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        // 提取方法中的request 和response参数
        Class<?>[] paramsTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < paramsTypes.length; i++) {
            Class<?> type = paramsTypes[i];
            if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                paramIndexMapping.put(type.getName(), i);
            }
        }

        // 获得方法的形参列表
        Map<String, String[]> params = request.getParameterMap();

        // 实参列表
        Object[] paramValues = new Object[paramsTypes.length];
        for (Entry<String, String[]> parm : params.entrySet()) {

            String value = Arrays.toString(parm.getValue()).replaceAll("\\[|\\]", "").replaceAll("\\s", ",");

            if (!paramIndexMapping.containsKey(parm.getKey())) {
                continue;
            }

            int index = paramIndexMapping.get(parm.getKey());

            paramValues[index] = caseStringValue(value, paramsTypes[index]);

        }

        if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }

        if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }

        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(), paramValues);
        if (null == result || result instanceof Void) {
            return null;
        }

        boolean isModeAndView = handlerMapping.getMethod().getReturnType() == GPModelAndView.class;
        if (isModeAndView) {
            return (GPModelAndView)result;
        }

        return null;
    }

    private Object caseStringValue(String value, Class<?> paramType) {
        if (String.class == paramType) {
            return value;
        }
        // 如果是int
        if (Integer.class == paramType) {
            return Integer.valueOf(value);
        } else if (Double.class == paramType) {
            return Double.valueOf(value);
        } else {
            if (value != null) {
                return value;
            }
            return null;
        }

    }

}
