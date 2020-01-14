package by.babanin.newsportalrest.config.jwt;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    public JwtSecurityConfigurer(
            @Qualifier("jwtAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint,
            JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
