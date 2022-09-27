package com.qzk.userservice.service;


import com.qzk.userservice.domain.dto.UserDto;
import com.qzk.userservice.domain.entity.User;

/**
 * @Description TODO
 * @Date 2022-09-06-15-52
 * @Author qianzhikang
 */

public interface UserService {
    /**
     * 根据id查用户
     * @param id id
     * @return User
     */
    User findById(Integer id);

    /**
     * 用户登陆
     * @param userDto 登陆信息
     * @return User
     */
    User login(UserDto userDto);

}
