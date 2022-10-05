package com.qzk.noticeservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.qzk.noticeservice.common.ResponseResult;
import com.qzk.noticeservice.common.ResultCode;
import com.qzk.noticeservice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022-09-06-17-30
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/notices")
@RefreshScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class NoticeController {

    @Value("${disabledNoticeService:false}")
    private boolean disabledNoticesService;
    private final NoticeService noticeService;

    @GetMapping("/latest")
    @SentinelResource(value = "getNotice", blockHandler = "noticeBlock")
    public ResponseResult getNotice() {
        if (disabledNoticesService) {
            return ResponseResult.failure(ResultCode.INTERFACE_FORBID_VISIT, "通知服务暂时关闭");
        } else {
            return ResponseResult.success(noticeService.getLatestNotice());
        }
    }

    @GetMapping("/page-notices")
    public ResponseResult getNoticePage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return ResponseResult.success(noticeService.getNotice(pageNum,pageSize));
    }


    public ResponseResult noticeBlock(BlockException e){
        log.info("限流方案");
        log.info(e.getMessage());
        return ResponseResult.failure(ResultCode.INTERFACE_FORBID_VISIT);
    }
}
