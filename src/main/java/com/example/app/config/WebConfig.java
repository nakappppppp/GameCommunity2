package com.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class WebConfig {

    // RequestContextListenerをBeanとして登録
    @Bean
     RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}

