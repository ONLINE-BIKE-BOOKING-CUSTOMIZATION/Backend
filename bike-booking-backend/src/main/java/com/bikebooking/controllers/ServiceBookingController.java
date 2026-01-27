package com.bikebooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.bikebooking.dto.*;
import com.bikebooking.enums.ServiceStatus;
import com.bikebooking.service.ServiceBookingService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceBookingController {

    @Autowired
    private ServiceBookingService serviceService;

    // ================= CUSTOMER APIs =================

    @PostMapping("/customer/services")
    public ResponseEntity<ApiResponse<ServiceBookingResponseDTO>> bookService(
            @RequestBody ServiceBookingRequestDTO request) {

        ServiceBookingResponseDTO response = serviceService.bookService(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Service booked successfully", response));
    }

    @GetMapping("/customer/services/{userId}")
    public ResponseEntity<ApiResponse<Page<ServiceBookingResponseDTO>>> getMyServices(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<ServiceBookingResponseDTO> services =
                serviceService.getMyServices(userId, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Services fetched successfully", services));
    }

    // ================= DEALER APIs =================

    @GetMapping("/dealer/services/{dealerId}")
    public ResponseEntity<ApiResponse<Page<ServiceBookingResponseDTO>>> getDealerServices(
            @PathVariable Long dealerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<ServiceBookingResponseDTO> services =
                serviceService.getDealerServices(dealerId, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Dealer services fetched successfully", services));
    }

    @PutMapping("/dealer/services/{serviceId}")
    public ResponseEntity<ApiResponse<String>> updateServiceStatus(
            @PathVariable Long serviceId,
            @RequestParam ServiceStatus status,
            @RequestParam Long dealerId) {

        serviceService.updateServiceStatus(serviceId, status, dealerId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Service status updated successfully", "UPDATED"));
    }

    // ================= ADMIN APIs =================

    @GetMapping("/admin/services")
    public ResponseEntity<ApiResponse<Page<ServiceBookingResponseDTO>>> getAllServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<ServiceBookingResponseDTO> services =
                serviceService.getAllServices(page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All services fetched successfully", services));
    }
}
