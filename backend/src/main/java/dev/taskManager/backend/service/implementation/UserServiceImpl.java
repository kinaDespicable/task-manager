package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.config.security.SecurityUser;
import dev.taskManager.backend.exception.exceptions.ResourceNotFoundException;
import dev.taskManager.backend.model.entity.Role;
import dev.taskManager.backend.model.entity.User;
import dev.taskManager.backend.model.request.role.NewRoleRequest;
import dev.taskManager.backend.model.request.role.UpdateRoleRequest;
import dev.taskManager.backend.repository.UserRepository;
import dev.taskManager.backend.service.RoleService;
import dev.taskManager.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("User with email: ["+username+"] not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public User fetchByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User with email: ["+email+"] not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public User fetchById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id: ["+id+"] not found."));
    }

    @Override
    @Transactional
    public User updateRole(Long id, UpdateRoleRequest request) {
        var user = fetchById(id);
        var role = roleService.fetchByName(request.role());
        user.setRole(role);
        var entity = userRepository.save(user);
        logger.info("Record has been updated. Record id: {}", id);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> fetchAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page,size));
    }
}
