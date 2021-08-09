package ang.neggaw.gateway.securities;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import reactor.core.publisher.Mono;

/**
 * Created by:
 *
 * @author ANG
 * @since 09-08-2021 22:10
 */

@Log4j2
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws JsonProcessingException {

        return http
                .csrf().disable()
                .httpBasic().and()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
                            log.error("authenticationEntryPoint: {} {}. Error message: {}",
                                    swe.getRequest().getRemoteAddress(), swe.getRequest().getPath(), e.getMessage());
                            swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        })
                ).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
                    log.error("accessDeniedHandler: {} {}. Error message: {}",
                            swe.getRequest().getRemoteAddress(), swe.getRequest().getPath(), e.getMessage());
                    swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                }))
                .and()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/user-microservice/auth/login", "/actuator/**").permitAll()
                .pathMatchers(authenticatedMicroservicesUrl()).permitAll()
                .anyExchange().authenticated().and()
                .headers().frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN).and()
                .build();
    }

    private static String[] authenticatedMicroservicesUrl() {
        return new String[]{
                "/employee-microservice/**",
                "/customer-microservice/**",
        };
    }

    @Bean
    public MapReactiveUserDetailsService mapReactiveUserDetailsService () {

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("gateway"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("gateway"))
                .roles("ADMIN", "USER")
                .build();
        return new MapReactiveUserDetailsService(user, admin);
    }
}