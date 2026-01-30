package com.bikebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealerInventoryRequestDTO {
    
    private Long dealerId; // Optional if resolved from UserID
    private Long bikeId;   // Required for Adding
    
    private Double price;
    private Integer stock;
    private String offer;
}
