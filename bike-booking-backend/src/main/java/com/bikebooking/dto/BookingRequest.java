package com.bikebooking.dto;

import java.util.List;

public class BookingRequest {

    private Long userId;
    private Long bikeId;
    private List<Long> customizationIds;

    // Getters & Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public List<Long> getCustomizationIds() {
        return customizationIds;
    }

    public void setCustomizationIds(List<Long> customizationIds) {
        this.customizationIds = customizationIds;
    }
}
