package com.qzk.noticeservice.repository;

import com.qzk.noticeservice.domain.entity.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * @Description TODO
 * @Date 2022-09-06-17-24
 * @Author qianzhikang
 */
public interface NoticeRepository extends JpaRepository<Notice,Integer> {
    /**
     * 根据类型提交查询，更具日期排序
     * @param showFlag 是否显示
     * @param sort 排序类型
     * @return List
     */
    List<Notice> findByShowFlag(Boolean showFlag, Sort sort);
}
