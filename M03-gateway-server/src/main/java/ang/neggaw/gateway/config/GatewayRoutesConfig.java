package ang.neggaw.gateway.config;

import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ANG
 * @since 24-07-2021 20:41
 */

@Configuration
public class GatewayRoutesConfig {

    // DynamicsRoutesConfig
    @Bean
    DiscoveryClientRouteDefinitionLocator dynamicsRoutes(ReactiveDiscoveryClient discoveryClient,
                                                         DiscoveryLocatorProperties locatorProperties) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, locatorProperties);
    }

    // routes Not dynamics config
    //@Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("employeeRoute", predicate -> predicate.path("/api/employees/**").uri("LB://EMPLOYEE-MICROSERVICE"))
                .build();
    }
}

