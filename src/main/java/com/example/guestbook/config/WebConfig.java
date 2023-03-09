package com.example.guestbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.guestbook.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] excludePathPatterns = {
                "/",
                "/members/join",
                "/members/login",
                "/members/logout",
                "/guestbooks/list",
                "/error",
                "/css/**",
                "/js/**",
                "*.ico"
        };

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }
}
