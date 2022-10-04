package com.qzk.contentservice.admincontroller;

import com.qzk.contentservice.auth.CheckAuthorization;
import com.qzk.contentservice.auth.CheckLogin;
import com.qzk.contentservice.common.ResponseResult;
import com.qzk.contentservice.domain.dto.AuditShareDto;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Date 2022-10-04-15-40
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final ShareService shareService;

    @PostMapping("/verify")
    @CheckLogin
    @CheckAuthorization("admin")
    public ResponseResult VerifyShares(@RequestBody AuditShareDto auditShareDto){
        Share share = shareService.auditShare(auditShareDto);
        return ResponseResult.success(share);
    }
}
