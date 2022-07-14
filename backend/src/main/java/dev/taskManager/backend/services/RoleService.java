package dev.taskManager.backend.services;

import dev.taskManager.backend.entities.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);

    Role fetchRoleById(Integer id);

    Role fetchRoleByRoleName(String name);

    List<Role> fetchAll();

    Role updateRole(Integer id, Role role);

    Boolean deleteRole(Integer id);
}
