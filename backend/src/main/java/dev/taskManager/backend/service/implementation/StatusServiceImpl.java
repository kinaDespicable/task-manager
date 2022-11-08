package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.repository.StatusRepository;
import dev.taskManager.backend.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
}
