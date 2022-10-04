package com.qzk.userservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.collect.Maps;
import com.qzk.userservice.auth.CheckLogin;
import com.qzk.userservice.common.ResponseResult;
import com.qzk.userservice.common.ResultCode;
import com.qzk.userservice.domain.dto.UserDto;
import com.qzk.userservice.domain.entity.User;
import com.qzk.userservice.service.UserService;
import com.qzk.userservice.utils.JwtOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    private final JwtOperator jwtOperator;

    @GetMapping("{id}")
    @CheckLogin
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
    public ResponseResult login(@RequestBody UserDto userDto){

        User user = userService.login(userDto);
        if (user == null){
            return ResponseResult.failure(ResultCode.USER_ACCOUNT_ERROR,"账号或密码错误");
        }
        HashMap<String, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("id", user.getId());
        objectObjectHashMap.put("role", user.getRoles());
        String token = jwtOperator.generateToken(objectObjectHashMap);
        return ResponseResult.success(token);
    }

}
