 package com.gp.vip.spring.framework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

public class GPViewResolver {

     private final String DEFAULT_TEMPLATE_SUFFX = ".html";
     
     private File templateRootDir;
     
     public GPViewResolver(String templateRoot) {
         String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
         templateRootDir = new File(templateRootPath);
     }
     
     public GPView resolverViewName(String viewName, Locale locale) throws Exception{
         if(null == viewName || "".equals(viewName.trim())) {
             return null;
         }
         
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() +"/"+viewName).replaceAll("/+", "/")); 
        
        return new GPView(templateFile);
     }
     
     
}
