package ang.neggaw.operations.securities;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author by: ANG
 * since: 11/09/2021 08:39
 */

@Log4j2
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@ConditionalOnProperty(value = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
@KeycloakConfiguration
@Import(KeycloakSpringBootConfigResolver.class)
public class KeycloakWebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {


    /**
     * Defines the session authentication strategy
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {

        // required for bearer-only applications
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();

        // simple authority Mapper to avoid ROLE_
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .formLogin().disable()

                // user previously declared bean
                .sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy()).and()

                // keycloak security filters
                .addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)
                .addFilterBefore(keycloakAuthenticationProcessingFilter(), X509AuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> {
                    log.error("Error: {}. Message: {}. Path: '{}'", HttpStatus.UNAUTHORIZED, e.getMessage(), request.getServletPath());
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
                }).and()
                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {

                    @Override
                    public void handle(HttpServletRequest request,
                                       HttpServletResponse response,
                                       AccessDeniedException e) throws IOException, ServletException {

                        log.error("Code: {}. Message: {}. Path: '{}'.", HttpStatus.FORBIDDEN, e.getMessage(), request.getServletPath());
                        response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
                    }
                }).and()

                // manage security routes
                .authorizeRequests()
                .antMatchers("/logout", "/login").permitAll()
                .antMatchers("/", "/operations/**", "/graphql", "/playground", "altair")
                .hasAnyRole("ADMIN", "admin_operation")
                .anyRequest().permitAll();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.OPTIONS.name(), "GET","POST"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
