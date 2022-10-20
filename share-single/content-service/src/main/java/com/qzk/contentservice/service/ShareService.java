package com.qzk.contentservice.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.qzk.contentservice.domain.dto.AuditShareDto;
import com.qzk.contentservice.domain.dto.ContributeDto;
import com.qzk.contentservice.domain.dto.ContributeRecordDto;
import com.qzk.contentservice.domain.dto.ShareQueryDto;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.domain.enums.ShareAuditEnums;
import org.springframework.data.domain.Page;

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
     * 分页资源
     * @param pageNum  当前页
     * @param pageSize 每页数量
     * @param shareQueryDto 模糊查询参数
     * @param userId 用户id
     * @return Page<Share>
     */
    Page<Share> getAll(int pageNum, int pageSize, ShareQueryDto shareQueryDto,Integer userId);

    /**
     * 获取分页资源
     * @param pageNum 当前页
     * @param pageSize 每页数量
     * @return 分页数据
     */
    Page<Share> getPageShare(int pageNum,int pageSize);

    /**
     * 审核分享内容
     * @param auditShareDto 分享内容dto
     * @return 分享内容详情
     */
    Share auditShare(AuditShareDto auditShareDto);


    /**
     * 兑换资源
     * @param shareId 资源id
     * @param userId 用户id
     * @return 兑换的资源
     */
    Share exchange(Integer shareId,Integer userId,String token) throws Exception;

    /**
     * 投稿
     * @param userId 用户id
     * @param contributeDto 投稿内容
     * @param token token
     * @return 投稿内容
     */
    Share contribute(Integer userId,String token, ContributeDto contributeDto);

    /**
     * 查询投稿记录
     * @param userId 用户id
     * @param pageNum 分页数据
     * @param pageSize 分页数据
     * @return 分页投稿记录
     */
    Page<ContributeRecordDto> getContributeRecord(Integer pageNum,Integer pageSize,Integer userId);

    //Sentinel测试
    //String getNumber();
    //
    //
    //String getNumberBlock(BlockException exception);
}
