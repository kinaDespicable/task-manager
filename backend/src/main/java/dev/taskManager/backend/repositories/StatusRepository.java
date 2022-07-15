package dev.taskManager.backend.repositories;

import dev.taskManager.backend.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {
    Optional<Status> findByStatusName(String statusName);
}
