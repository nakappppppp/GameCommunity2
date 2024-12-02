package com.example.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> bean = new FilterRegistrationBean<>(new AuthFilter());
        bean.addUrlPatterns("/admin"); // 対応するURL
        bean.setOrder(1); // フィルターの適用順
        return bean;
    }

//    @Bean
//    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
//        FilterRegistrationBean<LoggingFilter> bean = new FilterRegistrationBean<>(new LoggingFilter());
//        bean.addUrlPatterns("/**"); // すべてのURLパターン
//        bean.setOrder(2); // 認証フィルターより後に適用
//        return bean;
//    }
}
