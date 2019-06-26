package com.lhm.springcloud.security.utils;

import com.alibaba.fastjson.JSON;
import com.lhm.springcloud.security.constant.SecurityConstant;
import com.lhm.springcloud.security.entity.PermissionInfo;
import com.lhm.springcloud.security.entity.RoleInfo;
import com.lhm.springcloud.security.pojo.AuthUserDetails;
import com.lhm.springcloud.security.pojo.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName TokenUtil
 * @Description token工具类
 * @Author liuheming
 * @Date 2019/4/12 9:46
 * @Version 1.0
 **/
@Component
public class TokenUtil {
    /**
     * @Author liuheming
     * @Description 生成新的token方法，并将角色保存
     * @Date 17:18 2019/5/6
     * @Param [authUserDetails]
     * @return AccessJwtToken
     **/
    public String createAccessJwtToken(AuthUserDetails authUserDetails) {
        if (StringUtils.isBlank(authUserDetails.getUsername())) {
            throw new IllegalArgumentException("用户名为空无法创建token");
        }

        Claims claims = Jwts.claims().setSubject(authUserDetails.getUsername());

        //存入角色信息
        List<String> list=new ArrayList<>();
        for (RoleInfo roleInfo:authUserDetails.getRoleInfos()) {
            list.add(roleInfo.getId());
        }

        claims.put(SecurityConstant.AUTHORITIES, JSON.toJSONString(list));

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(SecurityConstant.tokenIssuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(SecurityConstant.tokenExpirationTime)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.tokenSigningKey)
                .compact();

        return token;
    }

    /**
     * @Author liuheming
     * @Description 创建刷新token
     * @Date 17:39 2019/5/6
     * @Param [authUserDetails]
     * @return com.lhm.springcloud.security.pojo.JwtToken
     **/
    public String createRefreshToken(AuthUserDetails authUserDetails) {
        if (StringUtils.isBlank(authUserDetails.getUsername())) {
            throw new IllegalArgumentException("用户名为空无法创建token");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(authUserDetails.getUsername());
        claims.put(SecurityConstant.AUTHORITIES, Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(SecurityConstant.tokenIssuer)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(SecurityConstant.refreshTokenExpTime)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.tokenSigningKey)
                .compact();

        return token;
    }

}
