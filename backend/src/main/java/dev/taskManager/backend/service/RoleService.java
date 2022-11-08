package dev.taskManager.backend.service;

import dev.taskManager.backend.model.entity.Role;
import dev.taskManager.backend.model.request.role.NewRoleRequest;
import dev.taskManager.backend.model.request.role.UpdateRoleRequest;
import dev.taskManager.backend.model.response.CreatedResponse;
import dev.taskManager.backend.model.response.DeletedResponse;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoleService {
    CreatedResponse create(NewRoleRequest roleRequest);

    Role fetchById(Long id);

    Role fetchByName(String roleName);

    Page<Role> fetchAll(int page, int size, Optional<String> sortBy);

    Role update(Long id, UpdateRoleRequest roleRequest);

    DeletedResponse delete(Long id);
}
