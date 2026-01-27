package com.bikebooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.bikebooking.dto.ApiResponse;
import com.bikebooking.dto.*;
import com.bikebooking.service.DealerService; // Assuming Service exists

@RestController
@RequestMapping("/api/dealer")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class DealerController {

    @Autowired
    private DealerService dealerService;

    // ================= PROFILE =================

    @GetMapping("/{dealerUserId}")
    public ResponseEntity<ApiResponse<com.bikebooking.dto.DealerResponseDTO>> getProfile(
            @PathVariable Long dealerUserId) {
        
        com.bikebooking.dto.DealerResponseDTO profile = dealerService.getProfile(dealerUserId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Profile fetched", profile));
    }

    @PutMapping("/{dealerUserId}")
    public ResponseEntity<ApiResponse<String>> updateProfile(
            @PathVariable Long dealerUserId,
            @RequestBody com.bikebooking.dto.DealerProfileUpdateRequestDTO request) {
        
        dealerService.updateProfile(dealerUserId, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Profile updated successfully", "UPDATED"));
    }

    // ================= INVENTORY =================

    @GetMapping("/{dealerUserId}/inventory")
    public ResponseEntity<ApiResponse<List<ShowroomBikeResponseDTO>>> getMyInventory(
            @PathVariable Long dealerUserId) {

        List<ShowroomBikeResponseDTO> inventory = dealerService.getInventory(dealerUserId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Inventory fetched", inventory));
    }

    @PostMapping("/{dealerUserId}/inventory")
    public ResponseEntity<ApiResponse<String>> addBikeToInventory(
            @PathVariable Long dealerUserId,
            @RequestBody DealerInventoryRequestDTO request) {

        dealerService.addBikeToInventory(dealerUserId, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Bike added to showroom", "ADDED"));
    }

    @PutMapping("/inventory/{dealerBikeId}")
    public ResponseEntity<ApiResponse<String>> updateInventory(
            @PathVariable Long dealerBikeId,
            @RequestBody DealerInventoryRequestDTO request) {

        dealerService.updateInventory(dealerBikeId, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Inventory updated", "UPDATED"));
    }

    @GetMapping("/{dealerUserId}/stats")
    public ResponseEntity<ApiResponse<com.bikebooking.dto.DealerStatsDTO>> getDealerStats(
            @PathVariable Long dealerUserId) {

        com.bikebooking.dto.DealerStatsDTO stats = dealerService.getDealerStats(dealerUserId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Dealer stats fetched", stats));
    }

    // ================= BOOKINGS =================

    @GetMapping("/{dealerUserId}/bookings/pending")
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getPendingBookings(
            @PathVariable Long dealerUserId) {

        List<BookingResponseDTO> bookings = dealerService.getPendingBookings(dealerUserId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Pending bookings fetched", bookings));
    }

    @PutMapping("/bookings/{bookingId}/accept")
    public ResponseEntity<ApiResponse<String>> acceptBooking(
            @PathVariable Long bookingId,
            @RequestParam("deliveryDate") java.time.LocalDate deliveryDate) {
        
        dealerService.acceptBooking(bookingId, deliveryDate);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Booking accepted", "ACCEPTED"));
    }

    @PutMapping("/bookings/{bookingId}/reject")
    public ResponseEntity<ApiResponse<String>> rejectBooking(@PathVariable Long bookingId) {
        dealerService.rejectBooking(bookingId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Booking rejected", "REJECTED"));
    }
}
