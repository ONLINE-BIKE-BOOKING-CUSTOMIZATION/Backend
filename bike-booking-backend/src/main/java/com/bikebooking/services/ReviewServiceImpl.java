package com.bikebooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikebooking.dto.*;
import com.bikebooking.entity.*;
import com.bikebooking.repository.*;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BikeRepository bikeRepository;

    // ================= CUSTOMER =================

    @Override
    public ReviewResponseDTO addReview(ReviewRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bike bike = bikeRepository.findById(request.getBikeId())
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        // Prevent duplicate review
        Review existing = reviewRepository
                .findByUser_UserIdAndBike_BikeId(request.getUserId(), request.getBikeId());

        if (existing != null) {
            throw new RuntimeException("You have already reviewed this bike");
        }

        // Rating validation
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.setUser(user);
        review.setBike(bike);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedBy(user.getUserId());

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByBike(Long bikeId) {

        return reviewRepository.findByBike_BikeIdAndIsDeletedFalse(bikeId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {

        return reviewRepository.findByUser_UserIdAndIsDeletedFalse(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= AGGREGATION =================

    @Override
    public Double getAverageRating(Long bikeId) {

        List<Review> reviews =
                reviewRepository.findByBike_BikeIdAndIsDeletedFalse(bikeId);

        if (reviews.isEmpty()) return 0.0;

        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }

        return sum / reviews.size();
    }

    // ================= MAPPER =================

    private ReviewResponseDTO mapToDTO(Review r) {

        ReviewResponseDTO dto = new ReviewResponseDTO();

        dto.setReviewId(r.getReviewId());
        dto.setRating(r.getRating());
        dto.setComment(r.getComment());
        dto.setUserName(r.getUser().getName());
        dto.setBikeName(r.getBike().getName());
        dto.setCreatedAt(r.getCreatedAt().toString());

        return dto;
    }
}
