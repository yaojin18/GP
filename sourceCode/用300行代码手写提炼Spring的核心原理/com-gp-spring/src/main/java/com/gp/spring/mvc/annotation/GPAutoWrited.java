 package com.gp.spring.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
/**
 * @author YAOJIN18
 * @date 2019/07/03
 */
public @interface GPAutoWrited {

   String name() default "";
}
