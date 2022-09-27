package com.qzk.userservice.repository;


import com.qzk.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Date 2022-09-06-16-10
 * @Author qianzhikang
 */

public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 根据手机和密码查用户
     * @param mobile 手机
     * @param password 密码
     * @return User
     */
    User findByMobileAndPassword(String mobile, String password);
}
