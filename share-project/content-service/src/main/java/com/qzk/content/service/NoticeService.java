package com.qzk.content.service;

import com.qzk.content.domain.entity.Notice;

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
}
