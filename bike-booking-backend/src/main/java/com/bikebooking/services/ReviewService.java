package com.bikebooking.service;

import java.util.List;

import com.bikebooking.dto.ReviewRequestDTO;
import com.bikebooking.dto.ReviewResponseDTO;

public interface ReviewService {

    // CUSTOMER
    ReviewResponseDTO addReview(ReviewRequestDTO request);

    List<ReviewResponseDTO> getReviewsByBike(Long bikeId);

    List<ReviewResponseDTO> getReviewsByUser(Long userId);

    Double getAverageRating(Long bikeId);
}
