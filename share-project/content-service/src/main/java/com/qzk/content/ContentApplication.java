package com.qzk.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description TODO
 * @Date 2022-09-06-16-44
 * @Author qianzhikang
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.qzk.content.openfeign"})
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class,args);
    }
}
