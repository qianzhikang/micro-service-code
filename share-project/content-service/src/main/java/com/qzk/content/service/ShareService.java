package com.qzk.content.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.qzk.content.domain.entity.Share;

import java.util.List;

/**
 * @Description TODO
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


    String getNumber();


    String getNumberBlock(BlockException exception);
}
