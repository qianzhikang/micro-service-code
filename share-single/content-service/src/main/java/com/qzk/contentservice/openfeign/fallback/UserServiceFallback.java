package com.qzk.contentservice.openfeign.fallback;


import com.qzk.contentservice.common.ResponseResult;
import com.qzk.contentservice.domain.entity.User;
import com.qzk.contentservice.openfeign.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2022-09-08-09-06
 * @Author qianzhikang
 */
@Component
@Slf4j
public class UserServiceFallback implements UserService {
    @Override
    public ResponseResult getUser(Integer id) {
        log.info("getUser fallback");
        User user = User.builder().avatar("test.png").nickname("降级方案用户").mobile("10000000000").build();
        return ResponseResult.success(user);
    }
}
