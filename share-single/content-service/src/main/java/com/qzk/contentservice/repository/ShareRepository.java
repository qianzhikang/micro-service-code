package com.qzk.contentservice.repository;

import com.qzk.contentservice.domain.dto.ContributeRecordDto;
import com.qzk.contentservice.domain.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022-09-06-16-54
 * @Author qianzhikang
 */
public interface ShareRepository extends JpaRepository<Share,Integer>, JpaSpecificationExecutor<Share> {
    /**
     * 根据是否显示查询
     * @param showFlag 是否显示
     * @param pageRequest 分页
     * @return 分页Share
     */
    Page<Share> findByShowFlag(Integer showFlag, PageRequest pageRequest);

    /**
     * 投稿记录查询
     * @param userId 用户id
     //* @param pageRequest 分页数据
     * @return 分页数据
     */
    @Query(value = "SELECT new com.qzk.contentservice.domain.dto.ContributeRecordDto(s.title,s.cover,s.createTime) FROM Share s WHERE s.userId =?1")
    Page<ContributeRecordDto> find(Integer userId,Pageable pageable);


}
