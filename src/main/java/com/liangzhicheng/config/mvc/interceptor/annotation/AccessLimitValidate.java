package com.liangzhicheng.config.mvc.interceptor.annotation;

import java.lang.annotation.*;

/**
 * @description 访问限制注解
 * @author liangzhicheng
 * @since 2020-08-07
 */
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimitValidate {

	boolean validate() default true;

	int second() default 1; //请求次数的指定时间范围，秒数(redis数据过期时间)

	int times() default 1; //指定second时间内API请求次数

}
