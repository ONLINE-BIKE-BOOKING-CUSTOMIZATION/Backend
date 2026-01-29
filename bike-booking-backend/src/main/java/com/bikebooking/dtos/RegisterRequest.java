package com.bikebooking.dto;

import com.bikebooking.enums.Role;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    // Common user fields
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role;

    // Address fields (for both dealer & customer)
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String pincode;

    // Dealer only fields
    private String showroomName;
    private String gstNumber;
    private String contactNumber;
}
