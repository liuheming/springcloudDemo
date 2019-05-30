package com.lhm.springcloud.security.config;

import com.ecwid.consul.v1.ConsulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RpsConsulServiceRegistryConfig
 * @Author liuheming
 * @Date 2019/4/26 15:07
 * @Version 1.0
 **/
@Configuration
public class RpsConsulServiceRegistryConfig {
    @Autowired(required = false)
    private TtlScheduler ttlScheduler;

    @Bean
    public MyConsulServiceRegistry consulServiceRegistry(
            ConsulClient consulClient,
            ConsulDiscoveryProperties properties,
            HeartbeatProperties heartbeatProperties) {
        return new MyConsulServiceRegistry(
                consulClient, properties, ttlScheduler, heartbeatProperties);
    }

    class MyConsulServiceRegistry extends ConsulServiceRegistry {

        public MyConsulServiceRegistry(ConsulClient client, ConsulDiscoveryProperties properties, TtlScheduler ttlScheduler, HeartbeatProperties heartbeatProperties) {
            super(client, properties, ttlScheduler, heartbeatProperties);
        }

        @Override
        public void register(ConsulRegistration reg) {
            reg.getService().setId(reg.getService().getName() + "-" + reg.getService().getAddress() + "-" + reg.getService().getPort());
            super.register(reg);
        }

    }
}
