package com.qzk.upload;

import com.qzk.upload.utils.AliyunResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UploadServiceApplicationTests {

    @Resource
    private AliyunResource aliyunResource;

    @Test
    void contextLoads() {
        System.out.println(aliyunResource.getAccessKeyId());
    }

}
