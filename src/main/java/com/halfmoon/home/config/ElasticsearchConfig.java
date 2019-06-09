package com.halfmoon.home.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ES的配置文件
 * @author: xuzhangwang
 */
@Configuration
public class ElasticsearchConfig {

    @Bean
    public TransportClient client() throws UnknownHostException {
        // 设置端口
        InetSocketTransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName("188.131.133.195"), 9300);

        Settings settings = Settings.builder().
                put("cluster.name", "es").
                build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);


        return client;
    }


}
