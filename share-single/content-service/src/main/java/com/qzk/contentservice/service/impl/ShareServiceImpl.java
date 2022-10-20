package com.qzk.contentservice.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.alibaba.fastjson.JSONObject;
import com.qzk.contentservice.common.ResponseResult;
import com.qzk.contentservice.domain.dto.*;
import com.qzk.contentservice.domain.entity.BonusEventLog;
import com.qzk.contentservice.domain.entity.MidUserShare;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.domain.entity.User;
import com.qzk.contentservice.domain.enums.ShareAuditEnums;
import com.qzk.contentservice.openfeign.UserService;
import com.qzk.contentservice.repository.BonusEventLogRepository;
import com.qzk.contentservice.repository.ShareRepository;
import com.qzk.contentservice.service.MidUserShareService;
import com.qzk.contentservice.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
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

    private final UserService userService;

    private final BonusEventLogRepository bonusEventLogRepository;


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
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
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
        if (userId == null) {
            all.forEach(share -> share.setDownloadUrl(null));
        } else {
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

    /**
     * 兑换资源
     *
     * @param shareId 资源id
     * @param userId  用户id
     * @return 兑换的资源
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Share exchange(Integer shareId, Integer userId, String token) throws Exception {
        // 查询资源单价
        Share share = shareRepository.findById(shareId).orElse(null);
        assert share != null;
        Integer price = share.getPrice();

        // 查询用户是否已经兑换过
        MidUserShare midUserShare = midUserShareService.selectRecordWithUserIdAndShareId(userId, shareId);
        // 是否兑换过
        if (midUserShare != null) {
            return share;
        } else {
            // openfeign调用远程服务获取用户信息
            ResponseResult result = userService.getUser(userId, token);
            String jsonStrings = JSONObject.toJSONString(result.getData());
            JSONObject jsonObject = JSONObject.parseObject(jsonStrings);
            User user = JSONObject.toJavaObject(jsonObject, User.class);
            if (user.getBonus() > price) {
                ResponseResult newData = userService.auditUserData(UserProfileAuditDto.builder().id(user.getId()).bonus(user.getBonus() - price).build(), token);
                String newDataStr = JSONObject.toJSONString(result.getData());
                JSONObject newJsonObj = JSONObject.parseObject(newDataStr);
                User newUser = JSONObject.toJavaObject(newJsonObj, User.class);

                // 插入用户兑换表记录
                midUserShareService.insert(MidUserShare.builder().shareId(share.getId()).userId(userId).build());

                // 修改兑换次数
                share.setBuyCount(share.getBuyCount() + 1);
                share = shareRepository.saveAndFlush(share);

                // 插入积分变动记录
                bonusEventLogRepository.saveAndFlush(BonusEventLog.builder()
                        .userId(userId)
                        .value("-" + price)
                        .event("EXCHANGE")
                        .createTime(new Date())
                        .description("兑换资源")
                        .build());

            } else {
                throw new Exception("积分不足");
            }
        }
        return share;
    }

    /**
     * @param userId        用户id
     * @param contributeDto 投稿内容
     * @return 投稿内容
     */
    @Override
    public Share contribute(Integer userId,String token, ContributeDto contributeDto) {
        ResponseResult result = userService.getUser(userId, token);
        String jsonStrings = JSONObject.toJSONString(result.getData());
        JSONObject jsonObject = JSONObject.parseObject(jsonStrings);
        User user = JSONObject.toJavaObject(jsonObject, User.class);

        Share share = Share.builder()
                .userId(user.getId())
                .buyCount(0)
                .auditStatus(ShareAuditEnums.NOT_YET.toString())
                .showFlag(0)
                .author(user.getNickname())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        BeanUtils.copyProperties(contributeDto,share);
        Share insert = shareRepository.saveAndFlush(share);
        return insert;
    }

    /**
     * 查询投稿记录
     *
     * @param pageNum  分页数据
     * @param pageSize 分页数据
     * @param userId   用户id
     * @return 分页投稿记录
     */
    @Override
    public Page<ContributeRecordDto> getContributeRecord(Integer pageNum, Integer pageSize, Integer userId) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createTime").descending());
        Page<ContributeRecordDto> myContribute = shareRepository.find(userId,pageable);
        return myContribute;
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
