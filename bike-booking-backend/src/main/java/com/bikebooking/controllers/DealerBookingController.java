package com.bikebooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bikebooking.dto.ApiResponse;
import com.bikebooking.dto.BookingResponseDTO;
import com.bikebooking.service.BookingService;

@RestController
@RequestMapping("/api/dealer/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class DealerBookingController {

    @Autowired
    private BookingService bookingService;

    // DEALER — VIEW ALL BOOKINGS
    @GetMapping("/all/{dealerId}")
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getAllBookings(
            @PathVariable Long dealerId) {

        List<BookingResponseDTO> list = bookingService.getBookingsByDealer(dealerId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All bookings fetched", list));
    }

    // DEALER — VIEW PENDING BOOKINGS
    @GetMapping("/pending/{dealerId}")
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getPendingBookings(
            @PathVariable Long dealerId) {

        List<BookingResponseDTO> list =
                bookingService.getPendingBookingsByDealer(dealerId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Pending bookings fetched", list));
    }

    // DEALER — ACCEPT BOOKING
    @PutMapping("/accept/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponseDTO>> acceptBooking(
            @PathVariable Long bookingId,
            @RequestParam("deliveryDate") java.time.LocalDate deliveryDate) {

        BookingResponseDTO response = bookingService.acceptBooking(bookingId, deliveryDate);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Booking accepted", response));
    }

    // DEALER — REJECT BOOKING
    @PutMapping("/reject/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponseDTO>> rejectBooking(
            @PathVariable Long bookingId) {

        BookingResponseDTO response = bookingService.rejectBooking(bookingId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Booking rejected", response));
    }
    
    
 // 🏪 DEALER — MARK BOOKING AS DELIVERED
    @PutMapping("/deliver/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponseDTO>> markAsDelivered(
            @PathVariable Long bookingId) {

        BookingResponseDTO response = bookingService.markAsDelivered(bookingId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Booking marked as delivered", response));
    }

    
    
}




