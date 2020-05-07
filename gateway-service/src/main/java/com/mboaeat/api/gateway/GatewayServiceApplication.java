package com.mboaeat.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class);
    }

    /*
    public RouteLocator routeLocator(RouteLocatorBuilder builder){

        return builder.routes()
                .route(
                        "account", predicateSpec -> {
                            return predicateSpec.uri("lb://account-service/")
                                    .filter(
                                            (exchange, chain) -> exchange.transformUrl()
                                    );
                        }
                ).build();
    }

     */
}
