package com.qzk.contentservice.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.qzk.contentservice.domain.dto.AuditShareDto;
import com.qzk.contentservice.domain.dto.ShareQueryDto;
import com.qzk.contentservice.domain.dto.UserAddBonusDto;
import com.qzk.contentservice.domain.entity.MidUserShare;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.domain.enums.ShareAuditEnums;
import com.qzk.contentservice.repository.ShareRepository;
import com.qzk.contentservice.service.MidUserShareService;
import com.qzk.contentservice.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    private final MidUserShareService midUserShareService;

    private final RocketMQTemplate rocketMQTemplate;


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
    public Page<Share> getAll(int pageNum, int pageSize, ShareQueryDto shareQueryDto, Integer userId) {
        // 分页规则
        Pageable pageable = PageRequest.of(pageNum, pageSize,Sort.by(Sort.Direction.DESC,"createTime"));
        // 条件查询
        Page<Share> all = shareRepository.findAll(new Specification<Share>() {
            @Override
            public Predicate toPredicate(Root<Share> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 是否显示条件
                predicates.add(criteriaBuilder.equal(root.get("showFlag").as(Integer.class), 1));
                // 处理查询封装对象空值问题
                if (shareQueryDto != null) {
                    // 拼接条件
                    if (shareQueryDto.getTitle() != null && !shareQueryDto.getTitle().equals("")) {
                        predicates.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + shareQueryDto.getTitle() + "%"));
                    }
                    if (shareQueryDto.getSummary() != null && !shareQueryDto.getSummary().equals("")) {
                        predicates.add(criteriaBuilder.like(root.get("summary").as(String.class), "%" + shareQueryDto.getSummary() + "%"));
                    }
                    if (shareQueryDto.getAuthor() != null && !shareQueryDto.getAuthor().equals("")) {
                        predicates.add(criteriaBuilder.like(root.get("author").as(String.class), "%" + shareQueryDto.getAuthor() + "%"));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[predicates.size()])));
            }
        }, pageable);
        if (userId == null){
            all.forEach(share -> share.setDownloadUrl(null));
        }else {
            all.forEach(share -> {
                Integer shareId = share.getId();
                MidUserShare midUserShare = midUserShareService.selectRecordWithUserIdAndShareId(userId, shareId);
                if (midUserShare == null) {
                    share.setDownloadUrl(null);
                }
            });
        }
        return all;
    }

    /**
     * 获取分页资源
     *
     * @param pageNum  当前页
     * @param pageSize 每页数量
     * @return 分页数据
     */
    @Override
    public Page<Share> getPageShare(int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by("createTime").descending());
        return shareRepository.findByShowFlag(1, pageRequest);
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
        if (!Objects.equals(ShareAuditEnums.NOT_YET.toString(), share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核！");
        }
        share.setAuditStatus(auditShareDto.getShareAuditEnums().toString());
        share.setReason(auditShareDto.getReason());
        share.setShowFlag(auditShareDto.getShowFlag() ? 1 : 0);
        // 持久化更新
        shareRepository.saveAndFlush(share);

        // 插入资源-用户关系表
        midUserShareService.insert(MidUserShare.builder().shareId(share.getId()).userId(share.getUserId()).build());

        // 往队列中添加一条加积分的数据
        if (ShareAuditEnums.PASS.equals(auditShareDto.getShareAuditEnums())) {
            rocketMQTemplate.convertAndSend("add-bonus", UserAddBonusDto.builder().userId(share.getUserId()).bonus(50).build());
        }
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
