package com.bikebooking.service;

import java.util.List;
import com.bikebooking.entity.Bike;
import com.bikebooking.entity.Dealer;
import com.bikebooking.dto.DealerStatsDTO; 

public interface AdminService {

    // Dealer Management
    List<Dealer> getPendingDealers();
    void verifyDealer(Long dealerId);
    void rejectDealer(Long dealerId);

    // Bike Management
    Bike addMasterBike(Bike bike);
    Bike updateMasterBike(Long bikeId, Bike bike);
    
    // Stats (Reuse DealerStatsDTO or create new AdminStatsDTO? Let's generic map for now or simple count)
    // Actually, let's return a simple map or DTO.
    java.util.Map<String, Object> getGlobalStats();

    // PROFILE
    com.bikebooking.dto.AdminProfileResponseDTO getProfile(Long adminId);
    void updateProfile(Long adminId, com.bikebooking.dto.AdminProfileUpdateRequestDTO request);
}
