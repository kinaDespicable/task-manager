package dev.taskManager.backend.config.security.filter;

import dev.taskManager.backend.config.beans.Utils;
import dev.taskManager.backend.config.jwt.JwtProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().startsWith("/auth")){
            filterChain.doFilter(request,response);
        }else{
            String header = request.getHeader(AUTHORIZATION);
            logger.info(request.getServletPath());
            if(header!= null && header.startsWith("Bearer ")){
                try{
                    final String token = header.substring("Bearer ".length());
                    logger.info("Token: {}", token);
                    var authenticationToken = getAuthentication(token);
                    logger.info("Authentication: {}", authenticationToken);
                    contextSetUp(authenticationToken);
                    filterChain.doFilter(request,response);
                }catch (Exception e){
                    logger.error("Error occurred: {}", e.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    Map<String,String> responseBody = new HashMap<>();
                    responseBody.put("error_type", e.getClass().getSimpleName());
                    responseBody.put("error", e.getMessage());
                    Utils.generateResponse(response, responseBody);
                }
            }
            else{
                filterChain.doFilter(request,response);
            }
        }
    }

    private Authentication getAuthentication(String token){
        String username = jwtProvider.getUsername(token);
        List<Map<String,String>> roles = jwtProvider.getAuthorities(token);
        var authorities = roles
                .stream()
                .map(role-> new SimpleGrantedAuthority(role.get("role")))
                .toList();
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
    private void contextSetUp(Authentication authentication){
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
