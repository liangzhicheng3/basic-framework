package com.liangzhicheng.config.swagger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

/**
 * @description swagger-bootstrap-ui接口文档配置
 * 1.pom文件引入swagger-ui、swagger-bootstrap-ui依赖
 * 2.创建SwaggerConfig类(构建接口文档页面功能展示信息)
 * 3.创建WebMvcConfig类(注册并初始化接口文档页面)
 * @author liangzhicheng
 * @since 2020-07-28
 */
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
@SpringBootApplication
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
