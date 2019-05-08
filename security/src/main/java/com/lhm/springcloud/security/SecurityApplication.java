package com.lhm.springcloud.security;

import com.lhm.springcloud.security.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.lhm.springcloud.security.mapper")
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SecurityApplication.class);
		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
		// 将Context设置到SpringUtil中
		SpringUtil.setApplicationContext(configurableApplicationContext);
	}

}
