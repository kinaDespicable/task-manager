package dev.taskManager.backend.service;

import dev.taskManager.backend.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User fetchByEmail(String email);
}
