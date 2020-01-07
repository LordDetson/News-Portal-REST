package by.babanin.newsportalrest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationEntryPoint restAuthenticationEntryPoint;
    private final AuthenticationSuccessHandler RestAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler simpleUrlAuthenticationFailureHandler;

    public SecurityJavaConfig(
            AuthenticationEntryPoint restAuthenticationEntryPoint,
            AuthenticationSuccessHandler RestAuthenticationSuccessHandler
    ) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.RestAuthenticationSuccessHandler = RestAuthenticationSuccessHandler;
        this.simpleUrlAuthenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/foos").authenticated()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .successHandler(RestAuthenticationSuccessHandler)
                .failureHandler(simpleUrlAuthenticationFailureHandler)
                .and()
                .logout();
    }
}
