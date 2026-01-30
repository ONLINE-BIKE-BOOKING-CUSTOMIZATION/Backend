package com.bikebooking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealerStatsDTO {
    private double totalRevenue;
    private int totalBookings;
    private int pendingBookings;
    private int acceptedBookings;
    private int rejectedBookings;
    private int totalBikesSold; // Delivered/Accepted
}
