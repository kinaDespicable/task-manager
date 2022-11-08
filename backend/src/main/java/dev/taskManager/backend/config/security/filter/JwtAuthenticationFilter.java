package dev.taskManager.backend.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.taskManager.backend.config.beans.Utils;
import dev.taskManager.backend.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final String email;
        final String password;
        try{
            Map<String,String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            email = credentials.get("email");
            password = credentials.get("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        logger.info("Authentication token: {}", authenticationToken);
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("Authentication token: {}", authResult);
        final String accessToken = jwtProvider.generateAccessToken(authResult, request);
        final String refreshToken = jwtProvider.generateRefreshToken(authResult, request);
        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        Utils.generateResponse(response, tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", UNAUTHORIZED);
        responseBody.put("status_code", UNAUTHORIZED.value());
        responseBody.put("error_type", failed.getClass().getSimpleName());
        responseBody.put("error", failed.getMessage());
        Utils.generateResponse(response,responseBody);
    }
}
