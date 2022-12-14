package com.qzk.content.openfeign;

import com.qzk.content.common.ResponseResult;
import com.qzk.content.openfeign.fallback.UserServiceFallback;
import com.qzk.content.openfeign.fallback.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description TODO
 * @Date 2022-09-06-17-03
 * @Author qianzhikang
 */
//@FeignClient(value = "user-service",path = "/users",fallback = UserServiceFallback.class)
@FeignClient(value = "user-service",path = "/users",fallbackFactory = UserServiceFallbackFactory.class)
public interface UserService {
    @GetMapping("{id}")
    ResponseResult getUser(@PathVariable(value = "id") Integer id);
}
