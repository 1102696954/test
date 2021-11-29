package com.example.mobile.mobile.commons.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName MyConfig
 * @Description: 第二部使用拦截器生效
 * @Author ts-lingcai.kong
 * @Date 2021/11/29 13:48
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 使拦截器生效，参数1：拦截器名 ，参数2：拦截规则（/**）拦截全部
    registry
        .addInterceptor(new AppInterceptor()).addPathPatterns("/**");
    WebMvcConfigurer.super.addInterceptors(registry);
  }
}
