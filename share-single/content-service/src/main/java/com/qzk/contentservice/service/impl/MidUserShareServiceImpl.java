package com.qzk.contentservice.service.impl;

import com.qzk.contentservice.domain.entity.MidUserShare;
import com.qzk.contentservice.repository.MidUserShareRepository;
import com.qzk.contentservice.service.MidUserShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Date 2022-10-06-11-11
 * @Author qianzhikang
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MidUserShareServiceImpl implements MidUserShareService {

    private final MidUserShareRepository midUserShareRepository;

    /**
     * 插入一条 分享-用户 关系记录
     *
     * @param midUserShare 实体类信息
     */
    @Override
    public void insert(MidUserShare midUserShare) {
        midUserShareRepository.saveAndFlush(midUserShare);
    }
}
