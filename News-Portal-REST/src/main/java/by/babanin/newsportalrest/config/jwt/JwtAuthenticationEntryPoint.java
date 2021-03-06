package by.babanin.newsportalrest.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest req,
            HttpServletResponse resp,
            AuthenticationException e) throws IOException, ServletException {
        log.debug("Jwt authentication failed:" + e);
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED	, "Jwt authentication failed");
    }
}
