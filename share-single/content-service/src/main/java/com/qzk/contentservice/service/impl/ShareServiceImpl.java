package com.qzk.contentservice.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.qzk.contentservice.domain.dto.AuditShareDto;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.repository.ShareRepository;
import com.qzk.contentservice.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description 内容服务实现类
 * @Date 2022-09-06-16-55
 * @Author qianzhikang
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;


    /**
     * 根据id查询资源
     *
     * @param id id
     * @return Share
     */
    @Override
    public Share findById(Integer id) {
        return shareRepository.findById(id).orElse(null);
    }

    /**
     * 获取所有资源
     *
     * @return List
     */
    @Override
    public List<Share> getAll() {
        return shareRepository.findAll();
    }

    /**
     * 审核分享内容
     *
     * @param auditShareDto 分享内容dto
     * @return 分享内容详情
     */
    @Override
    public Share auditShare(AuditShareDto auditShareDto) {
        Share share = shareRepository.findById(auditShareDto.getId()).orElse(null);
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核！");
        }
        share.setAuditStatus(auditShareDto.getShareAuditEnums().toString());
        share.setReason(auditShareDto.getReason());
        share.setShowFlag(auditShareDto.getShowFlag() ? 1 : 0);
        shareRepository.saveAndFlush(share);
        return share;
    }

    //@Override
    //@SentinelResource(value = "getNumber",blockHandler = "getNumberBlock")
    //public String getNumber() {
    //    return "123";
    //}
    //
    //@Override
    //public String getNumberBlock(BlockException exception) {
    //    return "block";
    //}
}
