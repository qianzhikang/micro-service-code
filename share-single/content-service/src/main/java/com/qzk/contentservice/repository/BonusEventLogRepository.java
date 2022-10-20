package com.qzk.contentservice.repository;

import com.qzk.contentservice.domain.entity.BonusEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Date 2022-10-06-11-37
 * @Author qianzhikang
 */
public interface BonusEventLogRepository extends JpaRepository<BonusEventLog,Integer> {
}
