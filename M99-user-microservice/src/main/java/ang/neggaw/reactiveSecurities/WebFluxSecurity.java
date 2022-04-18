package ang.neggaw.reactiveSecurities;

import ang.neggaw.reactiveSecurities.filters.JwtWebFilter;
import ang.neggaw.reactiveSecurities.filters.SecurityContextRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * author by: ANG
 * since: 18/04/2022 18:47
 */

@Log4j2
@RequiredArgsConstructor
@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class WebFluxSecurity {

    private final SecurityContextRepository securityContextRepository;

    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtWebFilter jwtWebFilter) {

        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().and()
                .exceptionHandling()
                .authenticationEntryPoint((exchange, e) -> Mono.fromRunnable(() -> {
                    try {
                        errorExceptionHandling(exchange, e, HttpStatus.UNAUTHORIZED).subscribe();
                    } catch (JsonProcessingException eJon) {
                        eJon.printStackTrace();
                    }
                    log.error("authenticationEntryPoint: {} {}. Error message: {}",
                            exchange.getRequest().getRemoteAddress(), exchange.getRequest().getPath(), e.getMessage());
                }))
                .accessDeniedHandler((exchange, denied) -> Mono.fromRunnable(() -> {
                    try {
                        errorExceptionHandling(exchange, denied, HttpStatus.FORBIDDEN).subscribe();
                    } catch (JsonProcessingException eJon) {
                        eJon.printStackTrace();
                    }
                    log.error("accessDeniedHandler: {} {}. Error message: {}",
                            exchange.getRequest().getRemoteAddress(), exchange.getRequest().getPath(), denied.getMessage());
                })).and()
                .authorizeExchange()
                .pathMatchers(permittedUrl()).permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(HttpMethod.POST, authenticateUrl()).hasAnyAuthority("ADMIN", "USER")
                .pathMatchers(HttpMethod.GET, authenticateUrl()).hasAnyAuthority("ADMIN")
                .pathMatchers(HttpMethod.PUT, authenticateUrl()).hasAnyAuthority("ADMIN")
                .pathMatchers(HttpMethod.PATCH, authenticateUrl()).hasAnyAuthority("ADMIN")
                .pathMatchers(HttpMethod.DELETE, authenticateUrl()).hasAnyAuthority("ADMIN")
                .anyExchange().authenticated().and()
                .securityContextRepository(securityContextRepository)
                .addFilterAt(jwtWebFilter, SecurityWebFiltersOrder.FIRST)
                .headers().frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN)
                .and().build();

    }

    private static String[] permittedUrl() {
        return new String[] {
                "/css", "/js", "/images", "/webjars", "/favicon.ico", "/index", "/login", "/logout", "/home",
                "/auth/login", "/auth/me", "/h2/**", "/h2-console/**"
        };
    }

    private static String[] authenticateUrl() {
        return new String[] {
                "/api/**",
                "/users/**",
                "/roles/**"
        };
    }

    private Mono<Void> errorExceptionHandling(ServerWebExchange exchange, Exception e, HttpStatus httpStatus) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = objectMapper.writeValueAsString(
                Map.of(
                        "timesTamp", new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss")).format(new Date()),
                        "status", httpStatus.value(),
                        "error", httpStatus,
                        "message", e.getMessage(),
                        "remote address", Objects.requireNonNull(exchange.getRequest().getRemoteAddress()),
                        "path", exchange.getRequest().getPath().value()
                )
        );
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }
}