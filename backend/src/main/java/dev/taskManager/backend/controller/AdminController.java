package dev.taskManager.backend.controller;

import dev.taskManager.backend.config.utils.Utils;
import dev.taskManager.backend.model.request.role.UpdateRoleRequest;
import dev.taskManager.backend.model.request.user.NewUserRequest;
import dev.taskManager.backend.service.AuthService;
import dev.taskManager.backend.service.RoleService;
import dev.taskManager.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid NewUserRequest userRequest, HttpServletRequest request){
        boolean isAdmin = Utils.isAdmin(request);
        return new ResponseEntity<>(authService.register(userRequest, isAdmin), HttpStatus.CREATED);
    }

    @PatchMapping("/update/role/user/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody UpdateRoleRequest roleRequest){
        return new ResponseEntity<>(userService.updateRole(id,roleRequest), HttpStatus.OK);
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "p", required = false) Optional<Integer> p,
                                         @RequestParam(value = "s",required = false) Optional<Integer> s){
        int page = p.orElse(0);
        int size = p.orElse(10);
        return new ResponseEntity<>(userService.fetchAll(page, size),HttpStatus.OK);
    }

}
