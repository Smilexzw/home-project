package com.halfmoon.home.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xuzhangwang
 */
@Configuration
public class GsonConfig {

    @Bean
    public Gson gson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson;
    }

}
