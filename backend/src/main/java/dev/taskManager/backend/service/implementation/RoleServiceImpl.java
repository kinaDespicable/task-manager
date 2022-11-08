package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.exception.exceptions.ResourceAlreadyExistException;
import dev.taskManager.backend.exception.exceptions.ResourceNotFoundException;
import dev.taskManager.backend.exception.exceptions.RoleFormatException;
import dev.taskManager.backend.model.entity.Role;
import dev.taskManager.backend.model.request.role.NewRoleRequest;
import dev.taskManager.backend.model.request.role.UpdateRoleRequest;
import dev.taskManager.backend.model.response.CreatedResponse;
import dev.taskManager.backend.model.response.DeletedResponse;
import dev.taskManager.backend.repository.RoleRepository;
import dev.taskManager.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    public static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public CreatedResponse create(NewRoleRequest roleRequest) {
        var roleName = format(roleRequest.role());
        if(roleRepository.existsByRoleName(roleName))
            throw new ResourceAlreadyExistException("Role with name: ["+ roleRequest.role().trim()+"] already exist.");
        var entity = roleRepository.save(new Role(roleName));
        logger.info("Record has been inserted. Record id: {}.", entity.getId());
        return CreatedResponse.builder()
                .timestamp(Instant.now())
                .success(true)
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .data(entity)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Role fetchById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Role with id: ["+id+"] not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public Role fetchByName(String roleName) {
        return roleRepository.findByRoleName(format(roleName))
                .orElseThrow(()-> new ResourceNotFoundException("Role with name: ["+roleName+"] not found."));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> fetchAll(int page, int size, Optional<String> sortBy) {
        String sort = sortBy
                .orElse("id");
        return roleRepository.findAll(PageRequest.of(page,size, Sort.by(sort).ascending()));
    }

    @Override
    @Transactional
    public Role update(Long id, UpdateRoleRequest roleRequest) {
        var roleName = format(roleRequest.role());
        var role = fetchById(id);
        role.setRoleName(roleName);
        var entity = roleRepository.save(role);
        logger.info("Record has been updated. Record id: {}.", entity.getId());
        return entity;
    }

    @Override
    @Transactional
    public DeletedResponse delete(Long id) {
        var entity = fetchById(id);
        roleRepository.deleteById(id);
        logger.info("Record has been deleted. Record id: {}.", entity.getId());
        return DeletedResponse.builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .deleted(entity)
                .build();
    }


    private String format(String roleName){
        var role = roleName.trim().toUpperCase();
        if(role.isEmpty())
            throw new RoleFormatException("Role name cannot be empty.");
        if(role.startsWith("ROLE_") && role.length()==5)
            throw new RoleFormatException("Invalid role format.");
        if(!role.startsWith("ROLE_"))
            return "ROLE_"+role;
        return role;
    }
}
