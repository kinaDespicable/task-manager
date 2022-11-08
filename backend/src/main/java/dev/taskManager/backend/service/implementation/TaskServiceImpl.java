package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.repository.TaskRepository;
import dev.taskManager.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
}
