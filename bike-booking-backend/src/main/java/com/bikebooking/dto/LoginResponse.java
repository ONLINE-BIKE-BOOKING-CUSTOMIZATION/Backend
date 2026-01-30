package com.bikebooking.dto;

import com.bikebooking.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private Long userId;
    private String name;
    private String email;
    private Role role;
    private Boolean verified;   // for dealer
    private String message;
}
