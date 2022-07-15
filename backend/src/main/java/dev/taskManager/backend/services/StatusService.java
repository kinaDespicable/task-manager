package dev.taskManager.backend.services;

import dev.taskManager.backend.entities.Status;

import java.util.List;


public interface StatusService {
    Status createStatus(Status status);

    Status fetchById(Integer id);

    Status fetchByStatusName(String statusName);

    List<Status> fetchAll();

    Status updateStatus(Integer id, Status requestBody);

    Boolean deleteStatus(Integer id);
}
