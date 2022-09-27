package com.qzk.contentservice.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.repository.ShareRepository;
import com.qzk.contentservice.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
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

    @Override
    @SentinelResource(value = "getNumber",blockHandler = "getNumberBlock")
    public String getNumber() {
        return "123";
    }

    @Override
    public String getNumberBlock(BlockException exception) {
        return "block";
    }
}
