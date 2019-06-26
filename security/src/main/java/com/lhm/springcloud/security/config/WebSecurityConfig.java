package com.lhm.springcloud.security.config;


import com.lhm.springcloud.security.constant.IgnoredUrlsProperties;
import com.lhm.springcloud.security.filter.JWTAuthenticationFilter;
import com.lhm.springcloud.security.filter.MyFilterSecurityInterceptor;
import com.lhm.springcloud.security.filter.MyUsernamePasswordAuthenticationFilter;
import com.lhm.springcloud.security.filter.WebSecurityCorsFilter;
import com.lhm.springcloud.security.handler.EntryPointUnauthorizedHandler;
import com.lhm.springcloud.security.handler.RestAccessDeniedHandler;
import com.lhm.springcloud.security.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * Security 核心配置类
 * 开启控制权限至Controller
 * @author Exrickx
 * */


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failHandler;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        //密码加密使用 Spring Security 提供的BCryptPasswordEncoder.encode(user.getRawPassword().trim())
    }

    //修改AuthenticationManager的注入，解决http.getSharedObject(AuthenticationManager.class)无法获取AuthenticationManager实例的问题
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();
        myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);//设置登入处理方式
        myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);//设置登陆成功处理
        myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(failHandler); //设置登入失败处理

        JWTAuthenticationFilter jwtAuthenticationFilter=new JWTAuthenticationFilter(authenticationManager);

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        //除配置文件忽略路径其它所有请求都需经过认证和授权
        for(String url:ignoredUrlsProperties.getUrls()){
            registry.antMatchers(url).permitAll();
        }


        http
            //添加jtw鉴权过滤器
            .addFilterAt(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
            //添加自定义权限过滤器
            .addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class)
            //添加自定义登陆过滤器
            .addFilterAfter(myUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .cors()
        .and()
            //关闭csrf,所以请求可以访问
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(accessDeniedHandler)
            .and()
            //关闭session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            //登入方式  json:/v1/login
            .anyRequest().authenticated();


    }


}
