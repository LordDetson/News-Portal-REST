package by.babanin.newsportalrest.config;

import by.babanin.newsportalrest.config.jwt.JwtSecurityConfigurer;
import by.babanin.newsportalrest.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private String[] antPatternsPermitAll = {
            "/auth/signin",
            "/registration",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/v2/api-docs"
    };

    private final JwtSecurityConfigurer jwtSecurityConfigurer;

    public WebSecurityConfig(JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtSecurityConfigurer = jwtSecurityConfigurer;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(antPatternsPermitAll).permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                .anyRequest().hasRole(Role.ADMINISTRATOR.name())
                .and()
                .apply(jwtSecurityConfigurer);
    }
}
