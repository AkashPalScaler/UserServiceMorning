package com.scaler.userservicemorning.services;

import com.scaler.userservicemorning.models.Role;
import com.scaler.userservicemorning.models.User;
import com.scaler.userservicemorning.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User signup(String email, String password) {
        if(userRepository.existsByEmail(email)) {
            return null;
        }
        User user = new User();
        user.setEmail(email);
        user.setRole(Role.USER);
        user.setPasswordHash(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
