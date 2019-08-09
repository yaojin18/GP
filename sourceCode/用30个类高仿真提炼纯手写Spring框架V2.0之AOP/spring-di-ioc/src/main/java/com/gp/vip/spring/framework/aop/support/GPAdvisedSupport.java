package com.gp.vip.spring.framework.aop.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gp.vip.spring.framework.aop.aspect.GPAfterReturningAdviceInterceptor;
import com.gp.vip.spring.framework.aop.aspect.GPAfterThrowingAdviceInterceptor;
import com.gp.vip.spring.framework.aop.aspect.GPMethodBeforeAdviceInterceptor;
import com.gp.vip.spring.framework.aop.config.GPAopConfig;

public class GPAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private GPAopConfig config;

    private Pattern pointCutClassPattern;

    private transient Map<Method, List<Object>> methodCache;

    public GPAdvisedSupport(GPAopConfig config) {
        this.config = config;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public Object getTarget() {
        return this.target;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass)
        throws Exception {
        List<Object> cached = methodCache.get(method);
        if (cached == null) {

            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());

            cached = methodCache.get(m);

            this.methodCache.put(m, cached);

        }

        return cached;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    private void parse() {

        String pointCut = config.getPointCut().replaceAll("\\.", "\\\\.").replaceAll("\\\\.\\*", ".*")
            .replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");

        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern =
            Pattern.compile("class " + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));

        try {

            methodCache = new HashMap<Method, List<Object>>();
            Pattern pattern = Pattern.compile(pointCut);

            Class aspectClass = Class.forName(this.config.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<String, Method>();
            for (Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName(), m);
            }

            for (Method m : this.targetClass.getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()) {
                    // 执行器链
                    List<Object> advices = new LinkedList<Object>();
                    // 将每个方法包装成 MethodIterceptor
                    if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))) {
                        // 创建一个Advice

                        advices.add(new GPMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()),
                            aspectClass.newInstance()));
                    }

                    // after
                    if (!(null == config.getAspectAfter()) || "".equals(config.getAspectAfter())) {
                        advices.add(new GPAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()),
                            aspectClass.newInstance()));
                    }

                    // afterThrowing
                    if (!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))) {
                        GPAfterThrowingAdviceInterceptor throwingAdvice = new GPAfterThrowingAdviceInterceptor(
                            aspectMethods.get(config.getAspectAfterThrow()), aspectClass.newInstance());
                        throwingAdvice.setThrowName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdvice);
                    }

                    methodCache.put(m, advices);

                }

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

}
