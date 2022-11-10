package dev.taskManager.backend.config.security;

import dev.taskManager.backend.config.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.error("AUTHENTICATION PROCESS HAS BEEN FAILED.");

        Map<String,Object> responseBody = new LinkedHashMap<>();
        responseBody.put("success", false);
        responseBody.put("timestamp", Instant.now());
        responseBody.put("status", UNAUTHORIZED);
        responseBody.put("status_code", UNAUTHORIZED.value());
        responseBody.put("error_type", "AUTHENTICATION_ERROR");
        responseBody.put("error", authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Utils.generateResponse(response,responseBody);
    }
}
