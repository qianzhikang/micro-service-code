package com.example.gatewayservice.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2022-09-27-20-15
 * @Author qianzhikang
 */
//@Configuration
public class DynamicRoutesLoader implements InitializingBean {

    @Resource
    private NacosConfigManager configService;

    @Resource
    private NacosConfigProperties configProperties;

    @Resource
    private DynamicRoutesListener dynamicRoutesListener;


    // 指定监听的远程 JSON 文件
    private static final String ROUTES_CONFIG = "routes-config";


    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取远程路由文件内容
        String routes = configService.getConfigService().getConfig(ROUTES_CONFIG,configProperties.getGroup(),10000);
        dynamicRoutesListener.receiveConfigInfo(routes);
        // 注册监听器
        configService.getConfigService().addListener(ROUTES_CONFIG,configProperties.getGroup(),dynamicRoutesListener);
    }
}
