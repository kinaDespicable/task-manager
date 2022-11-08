package dev.taskManager.backend.repository;

import dev.taskManager.backend.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
