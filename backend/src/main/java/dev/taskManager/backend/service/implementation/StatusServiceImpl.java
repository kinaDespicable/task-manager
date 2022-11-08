package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.exception.exceptions.ResourceAlreadyExistException;
import dev.taskManager.backend.exception.exceptions.ResourceNotFoundException;
import dev.taskManager.backend.model.entity.Status;
import dev.taskManager.backend.model.request.status.NewStatusRequest;
import dev.taskManager.backend.model.request.status.UpdateStatusRequest;
import dev.taskManager.backend.model.response.CreatedResponse;
import dev.taskManager.backend.model.response.DeletedResponse;
import dev.taskManager.backend.repository.StatusRepository;
import dev.taskManager.backend.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    public static final Logger logger = LoggerFactory.getLogger(StatusServiceImpl.class);
    private final StatusRepository statusRepository;

    @Override
    @Transactional
    public CreatedResponse create(NewStatusRequest statusRequest) {
        var statusName = statusRequest.status().trim().toUpperCase();
        if(statusRepository.existsByStatusName(statusName))
            throw new ResourceAlreadyExistException("Status with name: ["+statusRequest.status().trim()+"] already exist.");
        var entity = statusRepository.save(new Status(statusName));
        logger.info("Record has been inserted. Record id: {}.", entity.getId());
        return CreatedResponse.builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .data(entity)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Status fetchById(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Status with id: ["+id+"] not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public Status fetchByName(String statusName) {
        return statusRepository.findByStatusName(statusName.trim().toUpperCase())
                .orElseThrow(()-> new ResourceNotFoundException("Status with id: ["+statusName.trim()+"] not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Status> fetchAll(int page, int size) {
        return statusRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    @Transactional
    public Status update(Long id, UpdateStatusRequest statusRequest) {
        var statusName = statusRequest.status().trim().toUpperCase();
        var status = fetchById(id);
        status.setStatusName(statusName);
        var entity = statusRepository.save(status);
        logger.info("Record has been updated. Record id: {}", entity.getId());
        return entity;
    }

    @Override
    @Transactional
    public DeletedResponse delete(Long id) {
        var entity = fetchById(id);
        statusRepository.deleteById(id);
        logger.info("Record has been deleted. Record id: {}", entity.getId());
        return DeletedResponse.builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .deleted(entity)
                .build();
    }

}
