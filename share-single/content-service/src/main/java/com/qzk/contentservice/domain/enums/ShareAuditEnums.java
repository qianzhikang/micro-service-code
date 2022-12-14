package com.qzk.contentservice.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description TODO
 * @Date 2022-10-04-18-00
 * @Author qianzhikang
 */
@Getter
@AllArgsConstructor
public enum ShareAuditEnums {
    /**
     * 等待审核
     */
    NOT_YET,
    /**
     * 审核通过
     */
    PASS,
    /**
     * 审核不通过
     */
    REJECT

}
