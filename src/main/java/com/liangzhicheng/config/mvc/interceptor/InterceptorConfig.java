package com.liangzhicheng.config.mvc.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description 拦截器相关配置
 * @author liangzhicheng
 * @since 2020-08-07
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    XSSInterceptor xssInterceptor(){
        return new XSSInterceptor();
    }

    @Bean
    AccessLimitInterceptor accessLimitInterceptor(){
        return new AccessLimitInterceptor();
    }

    @Bean
    LoginClientInterceptor loginClientInterceptor(){
        return new LoginClientInterceptor();
    }

    @Bean
    LoginServerInterceptor loginServerInterceptor(){
        return new LoginServerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(xssInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(accessLimitInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(loginClientInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(loginServerInterceptor()).addPathPatterns("/**");
    }

}
