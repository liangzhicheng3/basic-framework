package com.liangzhicheng;

import com.liangzhicheng.modules.controller.client.WebSocketClientManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//定时器注解，启用定时器功能
//@EnableScheduling
@SpringBootApplication
public class Run {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Run.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        //处理启动，WebSocket注入问题
        WebSocketClientManager.setApplicationContext(configurableApplicationContext);
    }

}
