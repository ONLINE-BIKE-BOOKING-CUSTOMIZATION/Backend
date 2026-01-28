package com.bikebooking.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {

    private Long customerId;
    private Long dealerId;
    private Long bikeId;

    private LocalDate deliveryDate;

    // ADVANCE or FULL
    private String paymentOption;
}
