package com.petcorner.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/adopt/v2/**")
                        .uri("lb://adopt-service"))
                .route(p -> p.path("/profile/v2/**")
                        .uri("lb://profile-service"))
                .route(p -> p.path("/therapy/v2/**")
                        .uri("lb://therapy-service"))
                .route(p -> p.path("/sitting/v2/**")
                        .uri("lb://sitting-service"))
                .route(p -> p.path("/trainer/v2/**")
                        .uri("lb://training-service"))
                .build();
    }

}
