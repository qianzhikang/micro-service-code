package com.qzk.noticeservice.service;


import com.qzk.noticeservice.domain.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @Description TODO
 * @Date 2022-09-06-17-26
 * @Author qianzhikang
 */
public interface NoticeService {
    /**
     * 获取通知
     * @return Notice
     */
    Notice getLatestNotice();

    /**
     * 获取通知
     * @param pageNum 开始页
     * @param pageSize 每页数量
     * @return 分页数据
     */
    Page<Notice> getNotice(int pageNum,int pageSize);
}
