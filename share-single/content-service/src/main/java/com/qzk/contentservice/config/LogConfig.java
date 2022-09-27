package com.qzk.contentservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2022-09-08-08-20
 * @Author qianzhikang
 */
@Configuration
public class LogConfig {
    @Bean
    Logger.Level feignLogger(){
        return Logger.Level.FULL;
    }
}
