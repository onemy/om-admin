package com.onemysoft.common.springmvc.intercept.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页自定义注解
 * @author onemysoft
 * 
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonDataRequestBody {
	String page() default "page";
	String start() default "start";
	String rows() default "rows";
}
