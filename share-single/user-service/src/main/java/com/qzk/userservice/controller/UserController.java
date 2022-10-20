package com.qzk.userservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.collect.Maps;
import com.qzk.userservice.auth.CheckLogin;
import com.qzk.userservice.common.ResponseResult;
import com.qzk.userservice.common.ResultCode;
import com.qzk.userservice.domain.dto.UserDto;
import com.qzk.userservice.domain.dto.UserProfileAuditDto;
import com.qzk.userservice.domain.entity.User;
import com.qzk.userservice.domain.vo.LoginVo;
import com.qzk.userservice.service.UserService;
import com.qzk.userservice.utils.JwtOperator;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
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
        System.out.println("查询..");
        return ResponseResult.success(userService.findById(id));
    }

    /**
     * 登陆接口
     * @param userDto 登陆凭证
     * @return token
     */
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
        return ResponseResult.success(LoginVo.builder().id(user.getId()).token(token).build());
    }


    /**
     * 修改个人信息接口
     * @param userProfileAuditDto 新的个人信息
     * @return 修改的后的用户信息
     */
    @CheckLogin
    @PostMapping("/audit")
    public ResponseResult auditProfile(@RequestBody UserProfileAuditDto userProfileAuditDto){
        System.out.println(userProfileAuditDto);
        User user = userService.auditProfile(userProfileAuditDto);
        if (user == null){
            return ResponseResult.failure(ResultCode.PARAM_IS_BLANK);
        }
        return ResponseResult.success(user);

    }

}
