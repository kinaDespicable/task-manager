package dev.taskManager.backend.repository;

import dev.taskManager.backend.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
