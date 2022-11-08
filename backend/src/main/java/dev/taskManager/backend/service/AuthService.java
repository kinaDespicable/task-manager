package dev.taskManager.backend.service;

import dev.taskManager.backend.model.request.user.NewUserRequest;
import dev.taskManager.backend.model.response.CreatedResponse;

public interface AuthService {
    CreatedResponse register(NewUserRequest userRequest, boolean isAdmin);
}
