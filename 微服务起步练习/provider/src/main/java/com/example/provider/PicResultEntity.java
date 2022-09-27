package com.example.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Date 2022-08-30-17-13
 * @Author qianzhikang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PicResultEntity {
    private String error;
    private int result;
    private int width;
    private int height;
    private String format;
    private String img;
}
