package com.lhm.springcloud.security.service.impl;

import com.lhm.springcloud.security.constant.ResultCode;
import com.lhm.springcloud.security.exception.CommonException;
import com.lhm.springcloud.security.pojo.AuthUserDetails;
import com.lhm.springcloud.security.pojo.AuthUserPoJo;
import com.lhm.springcloud.security.service.IUsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description  实现security提供的 用户信息获取接口  并按照业务增加redis 登陆限制
 * @Author liuheming
 * @Date 2019/5/6 10:26
 * @Version 1.0
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    //登入重试时间
    @Value("${security.loginAfterTime}")
    private Integer loginAfterTime;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUsersService iUsersService;

    /**
     * @Author liuheming
     * @Description 实现用户信息查询方法 让DaoAuthenticationProvider 获取到数据库获中用户数据
     * @Date 11:21 2019/5/6
     * @Param [username]
     * @return org.springframework.security.core.userdetails.UserDetails
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String flagKey = "loginFailFlag:"+username;
        String value = redisTemplate.opsForValue().get(flagKey);
        if(StringUtils.isNotBlank(value)){
            //超过限制次数
            throw new UsernameNotFoundException("登录错误次数超过限制，请"+loginAfterTime+"分钟后再试");
        }

        //查询用户信息
        AuthUserPoJo authUserPoJo=iUsersService.findAuthUserByUsername(username);
        if(null==authUserPoJo){
            throw new UsernameNotFoundException("当前用户不存在");
        }
        if(authUserPoJo.getRoleInfos()==null || authUserPoJo.getRoleInfos().isEmpty()){
            throw new UsernameNotFoundException("当前用户无角色");
        }
        return new AuthUserDetails(authUserPoJo);
    }
}
