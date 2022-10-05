package com.qzk.noticeservice.service.impl;



import com.qzk.noticeservice.domain.entity.Notice;
import com.qzk.noticeservice.repository.NoticeRepository;
import com.qzk.noticeservice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Date 2022-09-06-17-28
 * @Author qianzhikang
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * 获取通知
     *
     * @return Notice
     */
    @Override
    public Notice getLatestNotice() {
        Sort sort = Sort.by("createTime").descending();
        return noticeRepository.findByShowFlag(true, sort).get(0);
    }

    /**
     * 获取通知
     *
     * @param pageNum  开始页
     * @param pageSize 每页数量
     * @return 分页数据
     */
    @Override
    public Page<Notice> getNotice(int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by("createTime").descending());
        return noticeRepository.findByShowFlag(true, pageRequest);
    }
}
