package com.liangzhicheng.config.mvc.interceptor.annotation;

import java.lang.annotation.*;

/**
 * @description 权限校验注解
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionsValidate {

    boolean validate() default true;

    String expression(); //表达式

}
