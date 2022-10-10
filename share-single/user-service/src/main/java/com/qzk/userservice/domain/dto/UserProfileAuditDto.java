package com.qzk.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 修改个人信息传输类
 * @Date 2022-10-09-14-45
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileAuditDto {

    private Integer id;

    private String mobile;

    private String password;

    private String nickname;

    private String avatar;
}
