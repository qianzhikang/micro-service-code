package com.qzk.userservice.rocketmq;

import com.qzk.userservice.domain.dto.UserAddBonusDto;
import com.qzk.userservice.domain.entity.BonusEventLog;
import com.qzk.userservice.domain.entity.User;
import com.qzk.userservice.repository.BonusEventLogRepository;
import com.qzk.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-10-06-11-27
 * @Author qianzhikang
 */
@Service
@RocketMQMessageListener(consumerGroup = "consumer", topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusDto> {

    private final UserRepository userRepository;

    private final BonusEventLogRepository bonusEventLogRepository;


    @Override
    public void onMessage(UserAddBonusDto userAddBonusDto) {
        Integer userId = userAddBonusDto.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setBonus(user.getBonus() + userAddBonusDto.getBonus());
            userRepository.saveAndFlush(user);
        }
        bonusEventLogRepository.saveAndFlush(BonusEventLog.builder()
                .userId(userId).event("CONTRIBUTE")
                .createTime(new Date())
                .description("投稿积分")
                .build());
    }
}
