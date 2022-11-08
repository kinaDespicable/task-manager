package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.repository.RoleRepository;
import dev.taskManager.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
}
