package com.bikebooking.enums;

public enum BookingStatus {
    PENDING,        // created by customer
    ACCEPTED,       // accepted by dealer
    REJECTED,       // rejected by dealer
    CONFIRMED,      // payment done
    DELIVERED,      // bike delivered
    CANCELLED
}
