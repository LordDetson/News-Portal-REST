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
                .antMatchers("/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole(Role.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMINISTRATOR.name())
                .anyRequest().authenticated()
                .and()
                .apply(jwtSecurityConfigurer);
    }
}
