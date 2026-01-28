package com.bikebooking.service;

import java.util.List;
import com.bikebooking.dto.BookingResponseDTO;

public interface DealerService {

    List<BookingResponseDTO> getPendingBookings(Long dealerUserId);

    void acceptBooking(Long bookingId, java.time.LocalDate deliveryDate);

    void rejectBooking(Long bookingId);

    // INVENTORY
    List<com.bikebooking.dto.ShowroomBikeResponseDTO> getInventory(Long dealerUserId);

    void addBikeToInventory(Long dealerUserId, com.bikebooking.dto.DealerInventoryRequestDTO request);

    void updateInventory(Long dealerBikeId, com.bikebooking.dto.DealerInventoryRequestDTO request);

    // ANALYTICS
    com.bikebooking.dto.DealerStatsDTO getDealerStats(Long dealerUserId);

    // PROFILE
    com.bikebooking.dto.DealerResponseDTO getProfile(Long dealerUserId);
    void updateProfile(Long dealerUserId, com.bikebooking.dto.DealerProfileUpdateRequestDTO request);
}
