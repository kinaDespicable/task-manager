package dev.taskManager.backend.services;

import dev.taskManager.backend.dto.UserDTO;
import dev.taskManager.backend.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User register(UserDTO requestBody);

    List<UserDTO.ResponseDTO> fetchAll();

    User fetchOne(Long id);

    User fetchByEmail(String email);

    User updateUser(Long id, UserDTO requestBody);

    Boolean deleteUser(Long id);
}
