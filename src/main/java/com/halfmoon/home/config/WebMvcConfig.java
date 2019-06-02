package com.halfmoon.home.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author: xuzhangwang
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {



    /**
     * 静态资源加载配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }



    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
