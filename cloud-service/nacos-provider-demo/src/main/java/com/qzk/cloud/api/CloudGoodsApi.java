package com.qzk.cloud.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022-09-01-09-16
 * @Author qianzhikang
 */
@RestController
public class CloudGoodsApi {

    @Value("${server.port}")
    private String applicationServerPort;

    @GetMapping("/goodsServiceTest")
    public String goodsServiceTest(){
        return "this is goods service from port:" + applicationServerPort;
    }
}
