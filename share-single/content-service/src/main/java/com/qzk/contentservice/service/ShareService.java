package com.qzk.contentservice.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.qzk.contentservice.domain.dto.AuditShareDto;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.domain.enums.ShareAuditEnums;

import java.util.List;

/**
 * @Description 内容服务接口
 * @Date 2022-09-06-16-55
 * @Author qianzhikang
 */
public interface ShareService {
    /**
     * 根据id查询资源
     * @param id id
     * @return Share
     */
    Share findById(Integer id);

    /**
     * 获取所有资源
     * @return List
     */
    List<Share> getAll();


    /**
     * 审核分享内容
     * @param auditShareDto 分享内容dto
     * @return 分享内容详情
     */
    Share auditShare(AuditShareDto auditShareDto);

    //Sentinel测试
    //String getNumber();
    //
    //
    //String getNumberBlock(BlockException exception);
}
