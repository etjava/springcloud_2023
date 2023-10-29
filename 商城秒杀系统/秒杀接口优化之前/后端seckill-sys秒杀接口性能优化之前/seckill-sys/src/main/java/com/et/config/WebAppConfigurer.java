package com.et.config;

import com.et.interceptor.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理跨域问题
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .allowedOrigins("*")
                .maxAge(3600);
    }

    // 让Spring管理拦截器配置类
    @Bean
    public SysInterceptor sysInterceptor(){
        return new SysInterceptor();
    }

    // 注册拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysInterceptor())
                .addPathPatterns("/**") // 对所有请求拦截
                .excludePathPatterns("/login","/logout","/image","/verifyCode/get"); // 不进行拦截的请求 多个之间使用逗号隔开 也可以直接传入一个String[]
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/image/**").
                addResourceLocations("file:D:/miaoshaimges/");// 注意 末尾必须加/否则不生效
    }
}
