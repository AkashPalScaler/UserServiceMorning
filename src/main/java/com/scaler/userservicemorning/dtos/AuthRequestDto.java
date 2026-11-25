package com.scaler.userservicemorning.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
}
// Common DTO for both signup and login (signup might have more details in real case like name, age, address etc)