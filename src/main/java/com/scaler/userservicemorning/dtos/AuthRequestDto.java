package com.scaler.userservicemorning.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    private String email;
    private String password;
}
// Common DTO for both signup and login (signup might have more details in real case like name, age, address etc)