package com.qzk.content.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.qzk.content.common.ResponseResult;
import com.qzk.content.common.ResultCode;
import com.qzk.content.domain.dto.ShareDto;
import com.qzk.content.domain.entity.Share;
import com.qzk.content.domain.entity.User;
import com.qzk.content.openfeign.UserService;
import com.qzk.content.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022-09-06-16-48
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/content")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareController {
    private final ShareService shareService;

    private final UserService userService;

    @GetMapping("{id}")
    @SentinelResource(value = "getShareById")
    public ResponseResult getShareById(@PathVariable Integer id){
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
    @SentinelResource(value = "getAllShares",blockHandler = "getAllBlock")
    public ResponseResult getAllShares() {
        return ResponseResult.success(shareService.getAll());
        //if (shareService.getNumber().equals("123")){
        //    return ResponseResult.success(shareService.getNumber());
        //}else {
        //    return ResponseResult.failure(ResultCode.INTERFACE_EXCEED_LOAD);
        //}
    }

    public ResponseResult getAllBlock(BlockException e){
        return ResponseResult.failure(ResultCode.INTERFACE_FORBID_VISIT);
    }

}
