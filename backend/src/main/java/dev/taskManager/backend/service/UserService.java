package dev.taskManager.backend.service;

import dev.taskManager.backend.model.entity.User;
import dev.taskManager.backend.model.request.role.UpdateRoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User fetchByEmail(String email);

    User fetchById(Long id);

    User updateRole(Long id, UpdateRoleRequest request);

    Page<User> fetchAll(int page, int size);
}
