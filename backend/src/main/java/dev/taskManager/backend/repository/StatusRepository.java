package dev.taskManager.backend.repository;

import dev.taskManager.backend.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    boolean existsByStatusName(String statusName);

    Optional<Status> findByStatusName(String statusName);

}
