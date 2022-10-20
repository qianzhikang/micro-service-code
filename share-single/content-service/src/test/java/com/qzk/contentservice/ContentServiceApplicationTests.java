package com.qzk.contentservice;

import com.qzk.contentservice.domain.dto.ShareQueryDto;
import com.qzk.contentservice.domain.entity.Notice;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.repository.NoticeRepository;
import com.qzk.contentservice.repository.ShareRepository;
import com.qzk.contentservice.service.ShareService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ContentServiceApplicationTests {

    @Resource
    NoticeRepository noticeRepository;

    @Resource
    ShareRepository shareRepository;


    @Resource
    ShareService shareService;
    @Test
    void contextLoads() throws Exception {
        //ShareQueryDto shareQueryDto = ShareQueryDto.builder().summary("Java").build();
        //Page<Share> java = shareService.getAll(0, 5,shareQueryDto,1);
        //java.forEach(item-> System.out.println("-->"+item));
        //shareService.exchange(1,2,"eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoidXNlciIsImlkIjoyLCJpYXQiOjE2NjYyMzI1OTcsImV4cCI6MTY2NzQ0MjE5N30.ZtSAmP7lmMwMwXsktOczatJcGtzTVQ2psW6KWI8vRB4");

    }

}
