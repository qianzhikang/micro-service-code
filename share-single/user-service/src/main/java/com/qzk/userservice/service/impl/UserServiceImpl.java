package com.qzk.userservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.qzk.userservice.domain.dto.UserDto;
import com.qzk.userservice.domain.dto.UserProfileAuditDto;
import com.qzk.userservice.domain.entity.BonusEventLog;
import com.qzk.userservice.domain.entity.User;
import com.qzk.userservice.repository.BonusEventLogRepository;
import com.qzk.userservice.repository.UserRepository;
import com.qzk.userservice.service.UserService;
import com.qzk.userservice.utils.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    private final BonusEventLogRepository bonusEventLogRepository;

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

    /**
     * 修改个人信息
     *
     * @param userProfileAuditDto 新的用户个人信息
     * @return 新用户信息
     */
    @Override
    public User auditProfile(UserProfileAuditDto userProfileAuditDto) {
        User user = userRepository.findById(userProfileAuditDto.getId()).orElse(null);
        if (user != null) {
            // hutools 赋值转换工具
            BeanUtil.copyProperties(userProfileAuditDto,user, CopyOptions.create().ignoreNullValue());
            log.info(user.toString());
            return userRepository.saveAndFlush(user);
        } else {
            return null;
        }

    }

    /**
     * 获取积分明细
     *
     * @param userId   用户id
     * @param pageNum  分页
     * @param pageSize 分页
     * @return 分页积分明细
     */
    @Override
    public Page<BonusEventLog> getBonusRecord(Integer userId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createTime").descending());
        return bonusEventLogRepository.findByUserId(userId,pageable);
    }
}
