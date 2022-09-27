package com.example.provider.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022-08-30-15-39
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("hello")
    public String sayHello(){
        return "hello";
    }
}
