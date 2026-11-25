package com.scaler.userservicemorning.controller;

import com.scaler.userservicemorning.dtos.AuthRequestDto;
import com.scaler.userservicemorning.dtos.LoginResponseDto;
import com.scaler.userservicemorning.dtos.UserDto;
import com.scaler.userservicemorning.models.Role;
import com.scaler.userservicemorning.models.User;
import com.scaler.userservicemorning.services.JwtService;
import com.scaler.userservicemorning.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/health")
    public String healthCheck(){
        return "OK";
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody AuthRequestDto authRequestDto) {
        User user = userService.signup(authRequestDto.getEmail(), authRequestDto.getPassword());
        if(user == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setId(user.getId());
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        String token = userService.login(authRequestDto.getEmail(), authRequestDto.getPassword());
        if(token == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<UserDto> validate(@RequestHeader("Authorization") String authHeader) {
        System.out.println("authHeader: " + authHeader);
        if(!authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            String token = authHeader.substring("Bearer ".length());
            Claims claims = jwtService.validateAndParseToken(token);
            UserDto userDto = new UserDto();
            userDto.setEmail(claims.getSubject());
            userDto.setRole(Role.valueOf(claims.get("role", String.class)));
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }catch (JwtException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
