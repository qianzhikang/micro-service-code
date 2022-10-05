package com.qzk.contentservice;

import com.qzk.contentservice.domain.entity.Notice;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.repository.NoticeRepository;
import com.qzk.contentservice.repository.ShareRepository;
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
    @Test
    void contextLoads() {
        Sort sort = Sort.by("createTime").descending();
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("createTime").descending());
        //Page<Notice> byShowFlag = noticeRepository.findByShowFlag(true, pageRequest);
        Page<Share> byShowFlag = shareRepository.findByShowFlag(1, pageRequest);
        System.out.println(byShowFlag.getContent());
        byShowFlag.forEach((item)-> System.out.println(item));
        //System.out.println();
    }

}
