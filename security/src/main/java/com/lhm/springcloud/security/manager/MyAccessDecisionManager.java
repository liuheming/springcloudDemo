package com.lhm.springcloud.security.manager;

import com.lhm.springcloud.security.pojo.MyGrantedAuthority;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @ClassName MyAccessDecisionManager
 * @Description 权限最终判断器
 *  * 判断用户拥有的角色是否有资源访问权限
 * @Author liuheming
 * @Date 2019/5/7 10:44
 * @Version 1.0
 **/
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    //decide方法判定当前请求路径是否拥有访问权限
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 对应url没有权限时，直接跳出方法
        if(configAttributes==null){
            return;
        }

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String url, method;
        AntPathRequestMatcher matcher;
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga instanceof MyGrantedAuthority) {
                MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
                url = urlGrantedAuthority.getPermissionUrl();
                method = urlGrantedAuthority.getMethod();
                matcher = new AntPathRequestMatcher(url);
                if (matcher.matches(request)) {
                    //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
                    if (method.equals(request.getMethod()) || "ALL".equals(method)) {
                        return;
                    }
                }
            } else if (ga.getAuthority().equals("ROLE_ANONYMOUS")){
                //未登录只允许访问 login 页面
                matcher = new AntPathRequestMatcher("/v1/login");
                if (matcher.matches(request)) {
                    return;
            }
        }
            throw new AccessDeniedException("您没有访问权限");
        }
        throw new AccessDeniedException("鉴权出错");
    }



    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
