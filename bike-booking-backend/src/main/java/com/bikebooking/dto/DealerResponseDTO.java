package com.bikebooking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealerResponseDTO {

    private Long dealerId;

    private String showroomName;
    private String gstNumber;
    private String contactNumber;

    private String city;
    private String state;
    private String pincode;
    private String address; // Line 1
    private String line2;

    private String ownerName;
    private String ownerEmail;

    private boolean verified;
}
