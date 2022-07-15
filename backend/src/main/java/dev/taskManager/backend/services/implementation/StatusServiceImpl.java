package dev.taskManager.backend.services.implementation;

import dev.taskManager.backend.entities.Status;
import dev.taskManager.backend.errorHandlers.exceptions.StatusAlreadyExistException;
import dev.taskManager.backend.errorHandlers.exceptions.StatusNotFoundException;
import dev.taskManager.backend.repositories.StatusRepository;
import dev.taskManager.backend.services.StatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status createStatus(Status status) {
        if(statusRepository.findByStatusName(status.getStatusName()).isPresent())
            throw new StatusAlreadyExistException("Status with name: ["+status.getStatusName()+"] already exist");
        return statusRepository.save(status);
    }

    @Override
    public Status fetchById(Integer id) {
        return statusRepository.findById(id)
                .orElseThrow(()-> new StatusNotFoundException("Status with id: ["+id+"] not found"));
    }

    @Override
    public Status fetchByStatusName(String statusName) {
        return statusRepository.findByStatusName(statusName)
                .orElseThrow(()-> new StatusNotFoundException("Status with name: ["+statusName+"] not found"));
    }

    @Override
    public List<Status> fetchAll() {
        return statusRepository.findAll();
    }

    @Override
    public Status updateStatus(Integer id, Status requestBody) {
        return statusRepository.findById(id)
                .map(fetched->{
                    fetched.setStatusName(requestBody.getStatusName());
                    return statusRepository.save(fetched);
                })
                .orElseThrow(()-> new StatusNotFoundException("Status with id: ["+id+"] not found"));
    }

    @Override
    public Boolean deleteStatus(Integer id) {
        statusRepository.deleteById(id);
        return true;
    }
}
