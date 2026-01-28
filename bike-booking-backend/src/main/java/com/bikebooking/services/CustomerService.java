package com.bikebooking.service;

import com.bikebooking.dto.CustomerProfileResponseDTO;
import com.bikebooking.dto.CustomerProfileUpdateRequestDTO;

public interface CustomerService {
    CustomerProfileResponseDTO getProfile(Long customerUserId);
    void updateProfile(Long customerUserId, CustomerProfileUpdateRequestDTO request);
}
