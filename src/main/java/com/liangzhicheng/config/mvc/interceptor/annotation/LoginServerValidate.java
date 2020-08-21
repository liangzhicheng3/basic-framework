package com.liangzhicheng.config.mvc.interceptor.annotation;

import java.lang.annotation.*;

/**
 * @description 服务端端登录校验注解
 * @author liangzhicheng
 * @since 2020-08-14
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginServerValidate {
	boolean validate() default true;
}
