package com.qzk.contentservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Date 2022-10-18-16-21
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareQueryDto {
    String title;
    String summary;
    String author;
}
