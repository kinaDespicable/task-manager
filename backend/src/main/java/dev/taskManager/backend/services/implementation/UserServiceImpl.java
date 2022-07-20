package dev.taskManager.backend.services.implementation;

import dev.taskManager.backend.dto.UserDTO;
import dev.taskManager.backend.entities.User;
import dev.taskManager.backend.errorHandlers.exceptions.PasswordMismatchException;
import dev.taskManager.backend.errorHandlers.exceptions.UserAlreadyExistException;
import dev.taskManager.backend.errorHandlers.exceptions.UserNotFoundException;
import dev.taskManager.backend.repositories.UserRepository;
import dev.taskManager.backend.services.RoleService;
import dev.taskManager.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User register(UserDTO requestBody) {

        if(userRepository.findByEmail(requestBody.getEmail()).isPresent())
            throw new UserAlreadyExistException("User with given email already exist");

        if(!requestBody.getPassword().equals(requestBody.getMatchingPassword()))
            throw new PasswordMismatchException("Password does not match.");

        User user = UserDTO.toEntity(requestBody);
        user.setPassword(passwordEncoder.encode(requestBody.getPassword()));
        user.setRole(roleService.fetchRoleByRoleName(requestBody.getRole()));

        return userRepository.save(user);
    }

    @Override
    public List<UserDTO.ResponseDTO> fetchAll() {
        List<UserDTO.ResponseDTO> list = new ArrayList<>();
        userRepository.findAll().forEach(
                user -> {
                    list.add(UserDTO.toResponseDTO(user));
                }
        );
        return list;
    }

    @Override
    public User fetchOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User with id ["+id+"] not found"));
    }

    @Override
    public User fetchByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User with email:["+email+"] not found"));
    }

    @Override
    public User updateUser(Long id, UserDTO requestBody) {
        User fetchedUser = fetchOne(id);

        if(Objects.nonNull(requestBody.getEmail()) && !"".equalsIgnoreCase(requestBody.getEmail()))
            fetchedUser.setEmail(requestBody.getEmail());
        if(Objects.nonNull(requestBody.getName()) && !"".equalsIgnoreCase(requestBody.getName()))
            fetchedUser.setName(requestBody.getName());
        if(Objects.nonNull(requestBody.getSurname()) && !"".equalsIgnoreCase(requestBody.getSurname()))
            fetchedUser.setSurname(requestBody.getSurname());
        if(Objects.nonNull(requestBody.getPassword()) && !"".equalsIgnoreCase(requestBody.getPassword()))
            fetchedUser.setPassword(requestBody.getPassword());

        return userRepository.save(fetchedUser);
    }

    @Override
    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
