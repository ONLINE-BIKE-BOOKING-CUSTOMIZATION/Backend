package com.bikebooking.dto;

import com.bikebooking.enums.BookingStatus;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {

    private Long bookingId;
    private BookingStatus status;

    private double totalAmount;
    private double paidAmount;
    private double remainingAmount;

    private String message;

    // UI Display Fields
    private String bikeName;
    private String showroomName;
    private String customerName;
    private String dealerCity;
    private String createdAt;
    private String deliveryDate;
    private String deliveryDate;
}
