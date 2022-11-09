package dev.taskManager.backend.controller;

import dev.taskManager.backend.config.utils.Utils;
import dev.taskManager.backend.config.jwt.JwtProvider;
import dev.taskManager.backend.model.entity.User;
import dev.taskManager.backend.model.request.user.NewUserRequest;
import dev.taskManager.backend.service.AuthService;
import dev.taskManager.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid NewUserRequest userRequest, HttpServletRequest request){
        boolean isAdmin = Utils.isAdmin(request);
        return new ResponseEntity<>(authService.register(userRequest, isAdmin), HttpStatus.CREATED);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String header =  request.getHeader(AUTHORIZATION);
        if(header!=null && header.startsWith("Bearer ")){
            try{
                final String refreshToken = header.substring("Bearer ".length());
                final String username = jwtProvider.getUsername(refreshToken);
                User user = userService.fetchByEmail(username);
                final String accessToken = jwtProvider.generateAccessToken(user, request);
                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                Utils.generateResponse(response, tokens);
            }catch (Exception e){
                logger.error("Error: {}", e.getMessage());
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("error_type", e.getClass().getSimpleName());
                responseBody.put("error", e.getMessage());
                Utils.generateResponse(response,responseBody);
            }

        }
    }
}
