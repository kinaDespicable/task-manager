package dev.taskManager.backend.config.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class JwtProvider {

    private static final int ACCESS_TOKEN_EXPIRATION = 5;
    private static final int REFRESH_TOKEN_EXPIRATION = 30;

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(Authentication authentication, HttpServletRequest request){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var roles = userDetails.getAuthorities()
                .stream().map(grantedAuthority -> new SimpleGrantedAuthority(grantedAuthority.getAuthority())).toList();
        return doTokenGeneration(userDetails, request, ACCESS_TOKEN_EXPIRATION)
                .claim("roles", roles)
                .compact();
    }
    public String generateRefreshToken(Authentication authentication, HttpServletRequest request){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return doTokenGeneration(userDetails, request, REFRESH_TOKEN_EXPIRATION).compact();
    }

    public String getUsername(String token){
        return getParser().parseClaimsJws(token).getBody().getSubject();
    }

    public List<Map<String,String>> getAuthorities(String token){
        System.out.println(getParser().parseClaimsJws(token).getBody().get("roles"));
        return (List<Map<String,String>>) getParser().parseClaimsJws(token).getBody().get("roles");
    }

    private JwtParser getParser(){
        return Jwts.parserBuilder().setSigningKey(getKey(secret)).build();
    }

    private Key getKey(String secret){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private JwtBuilder doTokenGeneration(UserDetails user, HttpServletRequest request, int expiration){
        return Jwts.builder()
                .setIssuer(request.getRequestURL().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(expiration, ChronoUnit.MINUTES)))
                .setSubject(user.getUsername())
                .signWith(getKey(secret));
    }


}
