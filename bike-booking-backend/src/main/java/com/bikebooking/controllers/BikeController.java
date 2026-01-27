package com.bikebooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.bikebooking.dto.*;
import com.bikebooking.service.BikeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @Autowired
    private com.bikebooking.service.CustomerSearchService customerSearchService;

    // ================= CUSTOMER APIs =================

    @GetMapping("/customer/bikes")
    public ResponseEntity<ApiResponse<List<BikeResponseDTO>>> getAllBikes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        List<BikeResponseDTO> bikesPage = bikeService.getAllBikes();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Bikes fetched successfully", bikesPage));
    }

    // Endpoint for Dealers to see ALL master bikes available to sell
    @GetMapping("/master/bikes")
    public ResponseEntity<ApiResponse<List<BikeResponseDTO>>> getMasterBikes() {
        List<BikeResponseDTO> bikes = bikeService.getMasterBikes();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Master bike list fetched", bikes));
    }

    @GetMapping("/customer/bikes/{bikeId}")
    public ResponseEntity<ApiResponse<BikeResponseDTO>> getBike(@PathVariable Long bikeId) {

        BikeResponseDTO bike = bikeService.getBikeById(bikeId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Bike fetched successfully", bike));
    }

    @GetMapping("/customer/dealers")
    public ResponseEntity<ApiResponse<List<ShowroomBikeResponseDTO>>> getDealersForBike(
            @RequestParam Long bikeId) {

        List<ShowroomBikeResponseDTO> dealers = customerSearchService.getDealersForBike(bikeId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Dealers fetched successfully", dealers));
    }
    @GetMapping("/debug/count")
    public ResponseEntity<String> getDebugData() {
        return ResponseEntity.ok("Backend Online. Call getAllBikes() to test DB.");
    }
}
