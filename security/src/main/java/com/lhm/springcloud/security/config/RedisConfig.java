package com.lhm.springcloud.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
class RedisConfig {

    private StringRedisTemplate template;

    @Autowired
    public RedisConfig(StringRedisTemplate template) {
        this.template = template;
    }

}