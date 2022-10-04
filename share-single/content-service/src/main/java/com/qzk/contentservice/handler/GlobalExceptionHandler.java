package com.qzk.contentservice.handler;

import com.qzk.contentservice.common.ResponseResult;
import com.qzk.contentservice.common.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description TODO
 * @Date 2022-10-04-16-10
 * @Author qianzhikang
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e){
        return ResponseResult.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR,e.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseBody
    public ResponseResult securityException(SecurityException securityException){
        return ResponseResult.failure(ResultCode.PERMISSION_NO_ACCESS,securityException.getMessage());
    }


    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseResult NullException(NullPointerException nullPointerException){
        return ResponseResult.failure(ResultCode.PARAM_IS_BLANK);
    }



}
