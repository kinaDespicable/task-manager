package dev.taskManager.backend.repository;

import dev.taskManager.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
