package com.ming.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author Ming
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Value("${files.blogImgs}")
    private String imgPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //博客图片地址映射
        registry.addResourceHandler("/blogImgs/**").addResourceLocations("file:"+imgPath);
        super.addResourceHandlers(registry);
    }
}
