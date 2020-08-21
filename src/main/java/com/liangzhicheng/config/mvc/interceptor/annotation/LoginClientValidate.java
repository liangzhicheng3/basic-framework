package com.liangzhicheng.config.mvc.interceptor.annotation;

import java.lang.annotation.*;

/**
 * @description 客户端登录校验注解
 * @author liangzhicheng
 * @since 2020-08-11
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginClientValidate {
	boolean validate() default true;
}
