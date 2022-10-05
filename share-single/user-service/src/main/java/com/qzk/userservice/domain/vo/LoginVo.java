package com.qzk.userservice.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 登陆返回实体类
 * @Date 2022-10-05-10-32
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginVo {
    private Integer id;
    private String token;
}
