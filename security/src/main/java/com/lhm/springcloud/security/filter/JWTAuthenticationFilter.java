package com.lhm.springcloud.security.filter;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhm.springcloud.security.constant.IgnoredUrlsProperties;
import com.lhm.springcloud.security.constant.ResultCode;
import com.lhm.springcloud.security.constant.SecurityConstant;
import com.lhm.springcloud.security.exception.CommonException;
import com.lhm.springcloud.security.pojo.MyGrantedAuthority;
import com.lhm.springcloud.security.utils.ResUtil;
import com.lhm.springcloud.security.utils.ResponseUtil;
import com.lhm.springcloud.security.utils.SpringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JWT过滤器
 */

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        IgnoredUrlsProperties ignoredUrlsProperties= SpringUtil.getBean("ignoredUrlsProperties", IgnoredUrlsProperties.class);
        String Requesturl=request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        if(null != ignoredUrlsProperties){
            for(String url:ignoredUrlsProperties.getUrls()){
                if(pathMatcher.match(url,Requesturl)){
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        //获取请求头
        String header = request.getHeader(SecurityConstant.HEADER);
        //如果请求头中不存在 或  格式不对  则进入下个过滤器
        if (StringUtils.isBlank(header) || !header.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {

            e.toString();
        }

        chain.doFilter(request, response);
    }

    /**
     * @Author liuheming
     * @Description 对token进行解析认证
     * @Date 11:11 2019/5/7
     * @Param [request, response]
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     **/
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws CommonException {

        String token = request.getHeader(SecurityConstant.HEADER);
        if (StringUtils.isNotBlank(token)) {
            // 解析token
            Claims claims = null;
            try {
                claims = Jwts.parser()
                        .setSigningKey(SecurityConstant.tokenSigningKey)
                        .parseClaimsJws(token.replace(SecurityConstant.TOKEN_SPLIT, ""))
                        .getBody();

                //获取用户名
                String username = claims.getSubject();

                //获取权限
                List<MyGrantedAuthority> authorities = new ArrayList<MyGrantedAuthority>();
                String authority = claims.get(SecurityConstant.AUTHORITIES).toString();

                if (StringUtils.isNotBlank(authority)) {
                    JSONArray list=JSONArray.parseArray(authority);
                    for (int i=0;i<list.size();i++){
                        JSONObject jsonObject=list.getJSONObject(i);
                        authorities.add(new MyGrantedAuthority(jsonObject.getString("path"),jsonObject.getString("method")));
                    }
                }
                if (StringUtils.isNotBlank(username)) {
                    //此处password不能为null
                    User principal = new User(username, "", authorities);
                    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
                }
            } catch (ExpiredJwtException e) {
                throw new CommonException(ResultCode.BAD_REQUEST, "登录已失效，请重新登录");
            } catch (Exception e) {

                ResponseUtil.out(response, ResUtil.getJsonStr(ResultCode.BAD_REQUEST, "解析token错误"));
            }
        }
        return null;
    }

}

