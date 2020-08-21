package com.liangzhicheng.config.mvc.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @description 请求跨域过滤配置
 * @author liangzhicheng
 * @since 2020-07-31
 */
@Configuration
public class CorsFilterConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration(); //添加Cors配置信息
        config.addAllowedOrigin("*"); //允许的域，如果填写*，Cookie则无法使用
        config.addAllowedHeader("*"); //允许任何头
        config.addAllowedMethod("*"); //允许任何方法(Post请求、Get请求等)
        config.setAllowCredentials(true); //是否发送Cookie信息
        //允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addAllowedHeader("*"); //允许的头信息
        config.setMaxAge(3600 * 24L); //配置有效时长
        //添加映射路径，拦截所有请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        //返回新的CorsFilter
        return new CorsFilter(configSource);
    }

}
