package com.qzk.contentservice.domain.dto;

import com.qzk.contentservice.domain.enums.ShareAuditEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 审核dto
 * @Date 2022-10-04-17-57
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditShareDto {
    private Integer id;
    private ShareAuditEnums shareAuditEnums;
    private String reason;
    private Boolean showFlag;
}
