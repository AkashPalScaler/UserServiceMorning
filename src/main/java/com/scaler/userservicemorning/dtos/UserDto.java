package com.scaler.userservicemorning.dtos;

import com.scaler.userservicemorning.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long  id;
    private String email;
    private Role role;
}
