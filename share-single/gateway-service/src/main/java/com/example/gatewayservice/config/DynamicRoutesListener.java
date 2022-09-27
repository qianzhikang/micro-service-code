package com.example.gatewayservice.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @Description TODO
 * @Date 2022-09-27-20-15
 * @Author qianzhikang
 */
@Component
public class DynamicRoutesListener implements Listener {
    @Resource
    private GatewayService gatewayService;


    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        System.out.println("received routes changes " + configInfo);

        List<RouteDefinition> definitionList = JSON.parseArray(configInfo,RouteDefinition.class);
        gatewayService.updateRoutes(definitionList);
    }
}
