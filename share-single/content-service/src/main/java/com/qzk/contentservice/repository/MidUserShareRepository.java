package com.qzk.contentservice.repository;

import com.qzk.contentservice.domain.entity.MidUserShare;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Date 2022-10-06-11-08
 * @Author qianzhikang
 */
public interface MidUserShareRepository extends JpaRepository<MidUserShare,Integer> {

    /**
     * 根据userID 和 shareID 查询记录
     * @param userId 用户id
     * @param shareId shareId
     * @return 记录
     */
    MidUserShare findByUserIdAndAndShareId(int userId,int shareId);
}
