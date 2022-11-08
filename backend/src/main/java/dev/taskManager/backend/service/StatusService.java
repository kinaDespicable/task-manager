package dev.taskManager.backend.service;

import dev.taskManager.backend.model.entity.Status;
import dev.taskManager.backend.model.request.status.NewStatusRequest;
import dev.taskManager.backend.model.request.status.UpdateStatusRequest;
import dev.taskManager.backend.model.response.CreatedResponse;
import dev.taskManager.backend.model.response.DeletedResponse;
import org.springframework.data.domain.Page;

public interface StatusService {
    CreatedResponse create(NewStatusRequest statusRequest);

    Status fetchById(Long id);

    Status fetchByName(String statusName);

    Page<Status> fetchAll(int page, int size);

    Status update(Long id, UpdateStatusRequest statusRequest);

    DeletedResponse delete(Long id);
}
