package dev.taskManager.backend.service.implementation;

import dev.taskManager.backend.config.security.SecurityUser;
import dev.taskManager.backend.repository.UserRepository;
import dev.taskManager.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("User with email: ["+username+"] not found."));
    }
}
