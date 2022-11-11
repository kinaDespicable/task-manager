package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.model.entity.Task;
import dev.taskManager.backend.model.entity.User;
import dev.taskManager.backend.model.request.task.NewTaskRequest;
import dev.taskManager.backend.model.response.CreatedResponse;
import dev.taskManager.backend.model.response.TaskSummary;
import dev.taskManager.backend.model.response.UserSummary;
import dev.taskManager.backend.repository.TaskRepository;
import dev.taskManager.backend.repository.UserRepository;
import dev.taskManager.backend.service.StatusService;
import dev.taskManager.backend.service.TaskService;
import dev.taskManager.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final StatusService statusService;


    @Override
    @Transactional
    public CreatedResponse create(NewTaskRequest taskRequest, Authentication authentication) {

        var creator = userService.fetchByEmail(authentication.getPrincipal().toString());
        var status = statusService.fetchByName("new");

        var responsible = taskRequest.responsible().stream()
                .map(userService::fetchByEmail)
                .filter(user-> !user.getRole().getRoleName().equals("ROLE_EMPLOYEE"))
                .collect(Collectors.toSet());
        var employee = taskRequest.employee().stream()
                .map(userService::fetchByEmail)
                .filter(user-> !user.getRole().getRoleName().equals("ROLE_ADMIN"))
                .collect(Collectors.toSet());

        var task = Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .status(status)
                .creator(creator)
                .responsible(responsible)
                .employee(employee)
                .creationDate(Instant.now())
                .deadline(taskRequest.deadline())
                .build();

        var entity = taskRepository.save(task);

        responsible.forEach(r-> r.getResponsibleFor().add(task));
        employee.forEach(e-> e.getWorksOn().add(task));


        return CreatedResponse.builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .data(task)
                .build();
    }

}
