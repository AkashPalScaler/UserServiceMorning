package com.scaler.userservicemorning.controller;

import com.scaler.userservicemorning.dtos.UserDto;
import com.scaler.userservicemorning.models.User;
import com.scaler.userservicemorning.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// /users/me - authenticated path - returning user info
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.get().getEmail());
        userDto.setRole(user.get().getRole());
        userDto.setId(user.get().getId());
        return ResponseEntity.ok(userDto);
    }
}
