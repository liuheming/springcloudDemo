package com.lhm.springcloud.security.handler;

import com.lhm.springcloud.security.constant.ResultCode;
import com.lhm.springcloud.security.utils.ResUtil;
import com.lhm.springcloud.security.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginFailureHandler
 * @Description 登陆失败处理过滤器
 * @Author liuheming
 * @Date 2019/5/7 9:05
 * @Version 1.0
 **/
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    //#限制用户登陆错误次数（次）
    @Value("${security.loginTimeLimit}")
    private Integer loginTimeLimit;
    //#错误超过次数后多少分钟后才能继续登录（分钟）
    @Value("${security.loginAfterTime}")
    private Integer loginAfterTime;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @Author liuheming
     * @Description 用户登陆失败处理类  记录用户登陆错误次数
     * @Date 9:12 2019/5/7
     * @Param [request, response, e]
     * @return void
     **/
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            String username = request.getParameter("username");
            recordLoginTime(username);
            String key = "loginTimeLimit:" + username;
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtils.isBlank(value)) {
                value = "0";
            }
            //获取已登录错误次数
            int loginFailTime = Integer.parseInt(value);
            int restLoginTime = loginTimeLimit - loginFailTime;
            ResponseUtil.out(response, ResUtil.getJsonStr(ResultCode.BAD_REQUEST, "用户名或密码错误"));
        } else if (e instanceof DisabledException) {
            ResponseUtil.out(response, ResUtil.getJsonStr(ResultCode.BAD_REQUEST, "账户被禁用，请联系管理员"));
        } else {
            ResponseUtil.out(response, ResUtil.getJsonStr(ResultCode.BAD_REQUEST, "登录失败"));
        }
    }
    /**
     * 判断用户登陆错误次数
     */
    public boolean recordLoginTime(String username) {

        String key = "loginTimeLimit:" + username;
        String flagKey = "loginFailFlag:" + username;
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) {
            value = "0";
        }
        //获取已登录错误次数
        int loginFailTime = Integer.parseInt(value) + 1;
        redisTemplate.opsForValue().set(key, String.valueOf(loginFailTime), loginAfterTime, TimeUnit.MINUTES);
        if (loginFailTime >= loginTimeLimit) {

            redisTemplate.opsForValue().set(flagKey, "fail", loginAfterTime, TimeUnit.MINUTES);
            return false;
        }
        return true;
    }
}
