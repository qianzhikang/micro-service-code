package com.qzk.contentservice.openfeign;

import com.qzk.contentservice.common.ResponseResult;
import com.qzk.contentservice.domain.dto.UserProfileAuditDto;
import com.qzk.contentservice.openfeign.fallback.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Date 2022-09-06-17-03
 * @Author qianzhikang
 */
//@FeignClient(value = "user-service",path = "/users",fallback = UserServiceFallback.class)
@FeignClient(value = "user-service",path = "/users",fallbackFactory = UserServiceFallbackFactory.class)
public interface UserService {
    @GetMapping("{id}")
    ResponseResult getUser(@PathVariable(value = "id") Integer id,@RequestHeader(name = "X-Token") String token);

    @PostMapping("/audit")
    ResponseResult auditUserData(@RequestBody UserProfileAuditDto userProfileAuditDto,@RequestHeader(name = "X-Token") String token);
}
