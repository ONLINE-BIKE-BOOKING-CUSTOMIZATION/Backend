package com.bikebooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.bikebooking.dto.*;
import com.bikebooking.service.ReviewService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // ================= CUSTOMER APIs =================

    // 🔹 ADD REVIEW
    @PostMapping("/customer/reviews")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> addReview(
            @RequestBody ReviewRequestDTO request) {

        ReviewResponseDTO response = reviewService.addReview(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Review added successfully", response));
    }

    // 🔹 GET MY REVIEWS
    @GetMapping("/customer/reviews/{userId}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> getMyReviews(
            @PathVariable Long userId) {

        List<ReviewResponseDTO> reviews =
                reviewService.getReviewsByUser(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User reviews fetched successfully", reviews));
    }

    // ================= PUBLIC / CUSTOMER APIs =================

    // 🔹 GET REVIEWS BY BIKE
    @GetMapping("/public/reviews/bike/{bikeId}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> getReviewsByBike(
            @PathVariable Long bikeId) {

        List<ReviewResponseDTO> reviews =
                reviewService.getReviewsByBike(bikeId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Bike reviews fetched successfully", reviews));
    }

    // 🔹 GET AVERAGE RATING
    @GetMapping("/public/reviews/bike/{bikeId}/average")
    public ResponseEntity<ApiResponse<Double>> getAverageRating(
            @PathVariable Long bikeId) {

        Double avg = reviewService.getAverageRating(bikeId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Average rating fetched successfully", avg));
    }
}
