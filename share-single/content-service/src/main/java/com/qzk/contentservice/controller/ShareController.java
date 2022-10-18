package com.qzk.contentservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.qzk.contentservice.common.ResponseResult;
import com.qzk.contentservice.common.ResultCode;
import com.qzk.contentservice.domain.dto.ShareDto;
import com.qzk.contentservice.domain.dto.ShareQueryDto;
import com.qzk.contentservice.domain.entity.Share;
import com.qzk.contentservice.domain.entity.User;
import com.qzk.contentservice.openfeign.UserService;
import com.qzk.contentservice.service.ShareService;
import com.qzk.contentservice.utils.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description TODO
 * @Date 2022-09-06-16-48
 * @Author qianzhikang
 */
@RestController
@Slf4j
@RequestMapping("/content")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareController {

    final Integer MAX_SIZE = 50;

    private final ShareService shareService;

    private final UserService userService;

    private final JwtOperator jwtOperator;

    @GetMapping("{id}")
    @SentinelResource(value = "getShareById")
    public ResponseResult getShareById(@PathVariable Integer id) {
        //if (shareService.getNumber().equals("123")){
        //    return ResponseResult.success(shareService.getNumber());
        //}else {
        //    return ResponseResult.failure(ResultCode.INTERFACE_EXCEED_LOAD);
        //}
        Share share = shareService.findById(id);
        ResponseResult result = userService.getUser(share.getUserId());
        String jsonStrings = JSONObject.toJSONString(result.getData());
        JSONObject jsonObject = JSONObject.parseObject(jsonStrings);
        User user = JSONObject.toJavaObject(jsonObject, User.class);
        return ResponseResult.success(ShareDto.builder().share(share).nickName(user.getNickname()).avatar(user.getAvatar()).build());
    }

    @GetMapping("/all")
    public ResponseResult getAllShares(@RequestParam(required = false, defaultValue = "0") int pageNum,
                                       @RequestParam(required = false, defaultValue = "5") int pageSize,
                                       @RequestBody(required = false) ShareQueryDto shareQueryDto,
                                       @RequestHeader(required = false, name = "X-Token") String token) {
        if (pageSize > MAX_SIZE) {
            pageSize = MAX_SIZE;
        }
        Integer userId = null;
        if (token != null) {
            userId = getUserIdFromToken(token);
        }
        return ResponseResult.success(shareService.getAll(pageNum, pageSize, shareQueryDto, userId));
    }


    @GetMapping("/page-shares")
    public ResponseResult getShares(@RequestParam int pageNum, @RequestParam int pageSize) {
        return ResponseResult.success(shareService.getPageShare(pageNum, pageSize));
    }

    public ResponseResult getAllBlock(BlockException e) {
        return ResponseResult.failure(ResultCode.INTERFACE_FORBID_VISIT);
    }


    private Integer getUserIdFromToken(String token) {
        return Integer.parseInt(jwtOperator.getClaimsFromToken(token).get("id").toString());
    }
}

