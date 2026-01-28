package com.bikebooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikebooking.entity.Bike;
import com.bikebooking.entity.Dealer;
import com.bikebooking.repository.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Dealer> getPendingDealers() {
        return dealerRepository.findByVerifiedFalse();
    }

    @Override
    public void verifyDealer(Long dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new RuntimeException("Dealer not found"));
        
        dealer.setVerified(true);
        dealerRepository.save(dealer);
    }

    @Override
    public void rejectDealer(Long dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new RuntimeException("Dealer not found"));
        
        // For now, we simply delete the dealer profile so they can re-apply or it's gone.
        // Or we could keep them and set a status if we had one.
        // Assuming delete for 'Reject' in MVP context.
        dealerRepository.delete(dealer);
    }

    @Override
    public Bike addMasterBike(Bike bike) {
        return bikeRepository.save(bike);
    }

    @Override
    public Bike updateMasterBike(Long bikeId, Bike bikeRequest) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike not found"));
        
        bike.setName(bikeRequest.getName());
        bike.setBrand(bikeRequest.getBrand());
        bike.setPrice(bikeRequest.getPrice());
        bike.setCc(bikeRequest.getCc());
        bike.setMileage(bikeRequest.getMileage());
        bike.setType(bikeRequest.getType());
        bike.setDescription(bikeRequest.getDescription());
        bike.setImageUrl(bikeRequest.getImageUrl());
        
        return bikeRepository.save(bike);
    }

    @Override
    public Map<String, Object> getGlobalStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalUsers = userRepository.count();
        long totalDealers = dealerRepository.count();
        long totalBookings = bookingRepository.count();
        long totalBikes = bikeRepository.count();
        
        // Calculate total revenue (sum of all booking totals)
        // Ideally use a custom query, but stream is okay for MVP
        double totalRevenue = bookingRepository.findAll().stream()
                .mapToDouble(b -> b.getTotalAmount())
                .sum();

        stats.put("totalUsers", totalUsers);
        stats.put("totalDealers", totalDealers);
        stats.put("totalBookings", totalBookings);
        stats.put("totalBikes", totalBikes);
        stats.put("totalRevenue", totalRevenue);
        
        return stats;
    }
    @Override
    public com.bikebooking.dto.AdminProfileResponseDTO getProfile(Long adminId) {
        com.bikebooking.entity.User user = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return com.bikebooking.dto.AdminProfileResponseDTO.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void updateProfile(Long adminId, com.bikebooking.dto.AdminProfileUpdateRequestDTO request) {
        com.bikebooking.entity.User user = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        user.setName(request.getName());
        userRepository.save(user);
    }
}
