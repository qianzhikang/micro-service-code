package com.qzk.userservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.qzk.userservice.common.ResponseResult;
import com.qzk.userservice.domain.dto.UserDto;
import com.qzk.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Date 2022-09-06-16-15
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    @SentinelResource(value = "getUserById")
    public ResponseResult getUserById(@PathVariable Integer id){
        //try {
        //    Thread.sleep(3000);
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
        //int cmd = 3/0;
        return ResponseResult.success(userService.findById(id));
    }

    @PostMapping("/login")
    public ResponseResult login(UserDto userDto){
        return ResponseResult.success(userService.login(userDto));
    }

}
