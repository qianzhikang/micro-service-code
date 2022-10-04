package com.qzk.userservice.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description 自定义鉴权注解
 * @Date 2022-10-04-13-59
 * @Author qianzhikang
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthorization {
    String value();
}
