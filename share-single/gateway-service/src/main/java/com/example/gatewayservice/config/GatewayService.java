package com.example.gatewayservice.config;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022-09-27-20-15
 * @Author qianzhikang
 */
@Service
public class GatewayService {
    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Resource
    private ApplicationEventPublisher publisher;

    public void updateRoutes(List<RouteDefinition> routes){
        if (CollectionUtils.isEmpty(routes)){
            System.out.println("No routes found");
            return;
        }
        routes.forEach(r->{
            try {
                routeDefinitionWriter.save(Mono.just(r)).subscribe();
                publisher.publishEvent(new RefreshRoutesEvent(this));
            }catch (Exception e){
                System.err.println("can not update route, id = " + r.getId());
            }
        });

    }
}
