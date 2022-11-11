package dev.taskManager.backend.service;

import dev.taskManager.backend.model.entity.Task;
import dev.taskManager.backend.model.request.status.UpdateStatusRequest;
import dev.taskManager.backend.model.request.task.NewTaskRequest;
import dev.taskManager.backend.model.response.CreatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface TaskService {
    CreatedResponse create(NewTaskRequest taskRequest, Authentication authentication);

    Task fetchById(Long id);

    Task updateStatus(Long id, UpdateStatusRequest statusRequest);
}
