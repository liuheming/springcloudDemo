package com.lhm.springcloud.security.config;

/**
 * @ClassName MybatisPlusConfig
 * @Description
 * @Author liuheming
 * @Date 2019/4/9 16:55
 * @Version 1.0
 **/

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    /**
     *   mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

}
