package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.exception.exceptions.PasswordMistmatchException;
import dev.taskManager.backend.exception.exceptions.ResourceAlreadyExistException;
import dev.taskManager.backend.model.entity.Role;
import dev.taskManager.backend.model.entity.User;
import dev.taskManager.backend.model.request.user.NewUserRequest;
import dev.taskManager.backend.model.response.CreatedResponse;
import dev.taskManager.backend.repository.UserRepository;
import dev.taskManager.backend.service.AuthService;
import dev.taskManager.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    @Transactional
    public CreatedResponse register(NewUserRequest userRequest, boolean isAdmin) {
        validate(userRequest);
        var role = isAdmin ?
                roleService.fetchByName(userRequest.role().trim().toUpperCase()) :
                roleService.fetchByName("EMPLOYEE");
        var user = User.builder()
                .email(userRequest.email())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .password(passwordEncoder.encode(userRequest.password()))
                .role(role)
                .build();

        var entity = userRepository.save(user);

        logger.info("Record has been inserted. Record id: {}", entity.getId());

        return CreatedResponse.builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .data(entity)
                .build();

    }

    void validate(NewUserRequest userRequest){
        if(userRepository.existsByEmail(userRequest.email())){
            logger.error("User with email: {} already exist.", userRequest.email());
            throw new ResourceAlreadyExistException("Email has already been taken.");
        }
        if(!userRequest.password().equals(userRequest.matchingPassword())){
            logger.error("Password does not match.");
            throw new PasswordMistmatchException("Passwords does not match.");
        }
    }
}
