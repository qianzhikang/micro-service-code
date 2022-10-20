package com.qzk.userservice.repository;

import com.qzk.userservice.domain.entity.BonusEventLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Date 2022-10-06-11-37
 * @Author qianzhikang
 */
public interface BonusEventLogRepository extends JpaRepository<BonusEventLog,Integer> {
    /**
     * 查询积分明细
     * @param userId 用户id
     * @param pageable 分页
     * @return 分页积分明细
     */
    Page<BonusEventLog> findByUserId(Integer userId, Pageable pageable);
}
