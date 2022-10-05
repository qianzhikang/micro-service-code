package com.qzk.contentservice.repository;

import com.qzk.contentservice.domain.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Date 2022-09-06-16-54
 * @Author qianzhikang
 */

public interface ShareRepository extends JpaRepository<Share,Integer> {
    /**
     * 根据是否显示查询
     * @param showFlag 是否显示
     * @param pageRequest 分页
     * @return 分页Share
     */
    Page<Share> findByShowFlag(Integer showFlag, PageRequest pageRequest);
}
