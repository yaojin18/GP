package com.gp.vip.spring.framework.beans.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gp.vip.spring.framework.beans.config.GPBeanDefinition;

public class GPBeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<String>();

    private Properties config = new Properties();

    private final String SCAN_PACKAGE = "scanPackage";

    public GPBeanDefinitionReader(String... configLoactions) {
        // TODO Auto-generated constructor stub
        InputStream is =
            this.getClass().getClassLoader().getResourceAsStream(configLoactions[0].replaceAll("classpath:", ""));

        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String scanPackage) {
        // TODO Auto-generated method stub

        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }

                String className = scanPackage + "." + file.getName().replace(".class", "");
                registryBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig() {
        return config;
    }

    public List<GPBeanDefinition> loadBeanDefinitions() {

        List<GPBeanDefinition> result = new ArrayList<GPBeanDefinition>();

        try {
            for (String className : registryBeanClasses) {

                Class<?> clazz = Class.forName(className);
                if (clazz.isInterface()) {
                    continue;
                }

                result.add(doCreateBeanDefinition(toLowerFirstCase(clazz.getSimpleName()), clazz.getName()));

                for (Class<?> i : clazz.getInterfaces()) {
                    result.add(doCreateBeanDefinition(i.getSimpleName(), clazz.getName()));
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    private GPBeanDefinition doCreateBeanDefinition(String simpleName, String name) {
        GPBeanDefinition beanDefinition = new GPBeanDefinition();
        beanDefinition.setBeanClassName(name);
        beanDefinition.setFactoryBeanName(simpleName);

        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;

        return new String(chars);
    }

}
