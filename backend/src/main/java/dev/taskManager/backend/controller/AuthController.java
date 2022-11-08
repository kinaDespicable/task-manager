package dev.taskManager.backend.controller;

import dev.taskManager.backend.model.request.user.NewUserRequest;
import dev.taskManager.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid NewUserRequest userRequest, HttpServletRequest request){
        boolean isAdmin = request.getRequestURI().contains("/admin/");
        return new ResponseEntity<>(authService.register(userRequest, isAdmin), HttpStatus.CREATED);
    }
}
