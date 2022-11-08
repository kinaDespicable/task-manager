package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.repository.UserRepository;
import dev.taskManager.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
}
