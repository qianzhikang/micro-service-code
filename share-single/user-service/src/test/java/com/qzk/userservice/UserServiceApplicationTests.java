package com.qzk.userservice;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.qzk.userservice.domain.dto.UserProfileAuditDto;
import com.qzk.userservice.domain.entity.User;
import com.qzk.userservice.repository.UserRepository;
import com.qzk.userservice.service.UserService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceApplicationTests {

    @Resource
    UserService userService;

    @Resource
    UserRepository userRepository;

    @Test
    void contextLoads() {
        userService.auditProfile(UserProfileAuditDto.builder().id(2).nickname("wd").build());
        //User user = userRepository.findById(2).orElse(null);
        //System.out.println(user);
        //UserProfileAuditDto wd = UserProfileAuditDto.builder().id(2).nickname("wd").build();
        //System.out.println(wd);
        //BeanUtil.copyProperties(wd,user, CopyOptions.create().ignoreNullValue());
        //System.out.println(user);

    }

}
