package com.qzk.contentservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Date 2022-10-06-11-16
 * @Author qianzhikang
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddBonusDto {
    private Integer userId;
    private Integer bonus;
}
