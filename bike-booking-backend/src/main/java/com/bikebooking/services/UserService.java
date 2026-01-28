package com.bikebooking.service;

import com.bikebooking.dto.LoginRequest;
import com.bikebooking.dto.LoginResponse;
import com.bikebooking.dto.RegisterRequest;

public interface UserService {

    String registerUser(RegisterRequest request);

    LoginResponse loginUser(LoginRequest request);

}
