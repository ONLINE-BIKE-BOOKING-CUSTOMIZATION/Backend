package com.bikebooking.dto;

import lombok.Data;

@Data
public class DealerProfileUpdateRequestDTO {
    private String name; // User name
    private String phone; // Dealer contact number
    private String address; // Line 1
    private String line2;
    private String city;
    private String state;
    private String pincode;
}
