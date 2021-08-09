package ang.neggaw.config.securities;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by:
 *
 * @author ANG
 * @since 09-08-2021 21:06
 */

@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("config")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().and()
                .formLogin().permitAll().and()
                .authorizeRequests()
                .antMatchers(permittedUrl()).permitAll()
                .anyRequest().hasRole("ADMIN");
    }

    private static String[] permittedUrl() {
        return new String[]{
                "/css", "/js", "/images", "/webjars", "/favicon.ico", "/index", "/login", "/logout", "/home"
        };
    }
}
