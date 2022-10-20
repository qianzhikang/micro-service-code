package com.qzk.userservice.service;


import com.qzk.userservice.domain.dto.UserDto;
import com.qzk.userservice.domain.dto.UserProfileAuditDto;
import com.qzk.userservice.domain.entity.BonusEventLog;
import com.qzk.userservice.domain.entity.User;
import org.springframework.data.domain.Page;

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


    /**
     * 修改个人信息
     * @param userProfileAuditDto 新的用户个人信息
     * @return 新用户信息
     */
    User auditProfile(UserProfileAuditDto userProfileAuditDto);

    /**
     * 获取积分明细
     * @param userId 用户id
     * @param pageNum 分页
     * @param pageSize 分页
     * @return 分页积分明细
     */
    Page<BonusEventLog> getBonusRecord(Integer userId,Integer pageNum,Integer pageSize);

}
