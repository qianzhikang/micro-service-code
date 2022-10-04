package com.qzk.userservice.service.impl;


import com.google.common.collect.Maps;
import com.qzk.userservice.domain.dto.UserDto;
import com.qzk.userservice.domain.entity.User;
import com.qzk.userservice.repository.UserRepository;
import com.qzk.userservice.service.UserService;
import com.qzk.userservice.utils.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Description TODO
 * @Date 2022-09-06-15-53
 * @Author qianzhikang
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtOperator jwtOperator;

    /**
     * 根据id查用户
     *
     * @param id id
     * @return User
     */
    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 用户登陆
     *
     * @param userDto 登陆信息
     * @return User
     */
    @Override
    public User login(UserDto userDto) {
        return userRepository.findByMobileAndPassword(userDto.getMobile(), userDto.getPassword());
    }
}
