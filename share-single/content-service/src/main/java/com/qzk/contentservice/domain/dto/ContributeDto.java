package com.qzk.contentservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-10-20-10-43
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContributeDto {

    private String title;

    private Integer isOriginal;

    private String cover;

    private String summary;

    private Integer price;

    private String downloadUrl;
}
