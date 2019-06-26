package com.lhm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

/*    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/mp/**")
                        .filters(f -> f
                                .stripPrefix(1))
                        .uri("lb://mp")
                        .id("mp")
                )
                .route(r -> r.path("/vote/**")
                        .filters(f -> f
                                .stripPrefix(1))
                        .uri("lb://iptv-activity-vote")
                        .id("iptv-activity-vote")
                )
                .build();
    }*/

}
