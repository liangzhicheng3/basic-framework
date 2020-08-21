package com.liangzhicheng.config.mvc.interceptor.annotation;

import java.lang.annotation.*;

/**
 * @description 防止DDOS攻击注解
 * @author liangzhicheng
 * @since 2020-08-07
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DDOSValidate {
	boolean validate() default true;
	int second() default 1;
}
