package dev.taskManager.backend.repository;

import dev.taskManager.backend.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
