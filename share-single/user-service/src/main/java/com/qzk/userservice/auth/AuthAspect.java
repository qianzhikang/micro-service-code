package com.qzk.userservice.auth;


import com.qzk.userservice.exception.SecurityException;
import com.qzk.userservice.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description TODO
 * @Date 2022-10-04-14-32
 * @Author qianzhikang
 */
@Aspect
@Component
public class AuthAspect {

    @Resource
    private JwtOperator jwtOperator;


    @Around("@annotation(com.qzk.userservice.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        checkToken();
        return point.proceed();
    }

    @Around("@annotation(com.qzk.userservice.auth.CheckAuthorization)")
    public Object checkAuthorization(ProceedingJoinPoint point) throws Throwable {
        try {
            checkToken();
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            String role = (String) httpServletRequest.getAttribute("role");
            MethodSignature signature = (MethodSignature)point.getSignature();
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);
            String value = annotation.value();
            if (!Objects.equals(role,value)){
                throw new SecurityException("该用户没有权限执行该操作");
            }
        }catch (Throwable throwable){
            throw new SecurityException("该用户没有权限执行该操作");
        }
        return point.proceed();
    }


    /**
     * 获取 HttpServletRequest
     * @return HttpServletRequest
     */
    private HttpServletRequest getHttpServletRequest(){
        // 请求上下文
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 强制转换为 ServletRequestAttributes
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        assert servletRequestAttributes != null;
        return servletRequestAttributes.getRequest();
    }

    /**
     * 检查 token 是否合法
     *
     */
    private void checkToken(){
        try {
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            String token = httpServletRequest.getHeader("X-Token");
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid){
                throw new SecurityException("token 不合法");
            }
            Claims claims = jwtOperator.getClaimsFromToken(token);
            httpServletRequest.setAttribute("id",claims.get("id"));
            httpServletRequest.setAttribute("wxNickname",claims.get("wxNickname"));
            httpServletRequest.setAttribute("role",claims.get("role"));
        }catch (Throwable throwable){
            throw new SecurityException("token 不合法");
        }
    }
}
