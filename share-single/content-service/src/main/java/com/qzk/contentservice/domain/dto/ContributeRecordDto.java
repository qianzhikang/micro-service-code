package com.qzk.contentservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-10-20-12-19
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContributeRecordDto {
    private String title;
    private String cover;
    private Date createTime;
}
