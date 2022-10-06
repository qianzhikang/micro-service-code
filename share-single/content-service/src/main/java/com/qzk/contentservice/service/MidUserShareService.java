package com.qzk.contentservice.service;

import com.qzk.contentservice.domain.entity.MidUserShare;

/**
 * @Description TODO
 * @Date 2022-10-06-11-10
 * @Author qianzhikang
 */
public interface MidUserShareService {
    /**
     * 插入一条 分享-用户 关系记录
     * @param midUserShare 实体类信息
     */
   void insert(MidUserShare midUserShare);
}
