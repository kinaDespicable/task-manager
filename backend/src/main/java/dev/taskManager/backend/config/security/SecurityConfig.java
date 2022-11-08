package dev.taskManager.backend.config.security;

import dev.taskManager.backend.config.jwt.JwtProvider;
import dev.taskManager.backend.config.security.filter.JwtAuthenticationFilter;
import dev.taskManager.backend.config.security.filter.JwtAuthorizationFilter;
import dev.taskManager.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(), jwtProvider);
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtProvider);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session-> session.sessionCreationPolicy(STATELESS))
                .authorizeRequests(request-> request.mvcMatchers("/auth/**").permitAll())
                .authorizeRequests(request-> request.mvcMatchers("/admin/**").hasRole("ADMIN"))
                .authorizeRequests(request-> request.mvcMatchers("/role/**").hasRole("ADMIN"))
                .authorizeRequests(request-> request.mvcMatchers("/status/**").hasRole("ADMIN"))
                .authorizeRequests(request-> request.anyRequest().authenticated())
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
