package com.lhm.springcloud.security.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhm.springcloud.security.entity.PermissionInfo;
import com.lhm.springcloud.security.service.IPermissionInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 权限资源管理器
 * 为权限决断器提供支持
 *
 * @author Exrickx
 */

@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private IPermissionInfoService findRoleInfoAndPermission;
    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 此方法判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 MyAccessDecisionManager的decide 方法，
     * 如果不在权限表中则放行。
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        Collection<ConfigAttribute> configAttributes=new ArrayList<>();
        //从reids中获取角色与权限数据
        String redisConfigAttributesPermission=redisTemplate.opsForValue().get("configAttributes:permissions");
        if(StringUtils.isBlank(redisConfigAttributesPermission)){
            QueryWrapper<PermissionInfo> permissionInfoQueryWrapper=new QueryWrapper<>();
            permissionInfoQueryWrapper.lambda().eq(PermissionInfo::getStatus,"enable");
            List<PermissionInfo> permissionInfos=findRoleInfoAndPermission.list(permissionInfoQueryWrapper);

            for (PermissionInfo permissionInfo:permissionInfos) {
                ConfigAttribute configAttribute=new SecurityConfig(permissionInfo.getPath()+" "+permissionInfo.getMethod());
                configAttributes.add(configAttribute);
            }
            //将权限存入redis
            redisTemplate.opsForValue().set("configAttributes:permissions", JSON.toJSONString(permissionInfos),480, TimeUnit.MINUTES);
        }else{
            JSONArray array=JSONObject.parseArray(redisConfigAttributesPermission);

            for (int i=0;i<array.size();i++) {
                JSONObject jsonObject=array.getJSONObject(i);
                ConfigAttribute configAttribute=new SecurityConfig(jsonObject.getString("path")+" "+jsonObject.getString("method"));
                configAttributes.add(configAttribute);
            }
        }

        //Object中包含用户请求request
        String url = ((FilterInvocation) o).getRequestUrl();
        String method=((FilterInvocation) o).getRequest().getMethod();
        String authPath=url+" "+method;//REST风格  根据方法判断权限

        PathMatcher pathMatcher = new AntPathMatcher();

        for (ConfigAttribute configAttribute:configAttributes) {
            if(StringUtils.isNotBlank(configAttribute.getAttribute())&&pathMatcher.match(configAttribute.getAttribute(),authPath)){
                return configAttributes;
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
