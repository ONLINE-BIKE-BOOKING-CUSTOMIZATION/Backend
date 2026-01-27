package com.bikebooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.bikebooking.dto.BikeRequestDTO;
import com.bikebooking.dto.BikeResponseDTO;
import com.bikebooking.entity.Bike;
import com.bikebooking.entity.Dealer;
import com.bikebooking.entity.DealerBike;
import com.bikebooking.repository.BikeRepository;
import com.bikebooking.repository.DealerBikeRepository;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private DealerBikeRepository dealerBikeRepository;

    // ================= ADMIN =================

    @Override
    public BikeResponseDTO addBike(BikeRequestDTO dto, Long adminId) {

        // Check if bike exists (deleted or active)
        Optional<Bike> existing = bikeRepository.findByNameAndBrand(dto.getName(), dto.getBrand());

        Bike bike;

        if (existing.isPresent()) {
            Bike found = existing.get();
            if (found.getIsDeleted()) {
                // REACTIVATE
                bike = found;
                bike.setIsDeleted(false);
            } else {
                throw new RuntimeException("Bike already exists: " + dto.getName());
            }
        } else {
            // CREATE NEW
            bike = new Bike();
        }

        // Set/Update Fields
        bike.setName(dto.getName());
        bike.setBrand(dto.getBrand());
        bike.setPrice(dto.getPrice());
        bike.setCc(dto.getCc());
        bike.setMileage(dto.getMileage());
        bike.setType(dto.getType());
        bike.setStock(dto.getStock());
        bike.setImageUrl(dto.getImageUrl());
        bike.setDescription(dto.getDescription());
        
        if (bike.getBikeId() == null) {
            bike.setCreatedBy(adminId);
        } else {
            bike.setUpdatedBy(adminId);
        }

        Bike saved = bikeRepository.save(bike);

        return mapToDTO(saved);
    }

    @Override
    public BikeResponseDTO updateBike(Long bikeId, BikeRequestDTO dto, Long adminId) {

        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        bike.setName(dto.getName());
        bike.setBrand(dto.getBrand());
        bike.setPrice(dto.getPrice());
        bike.setCc(dto.getCc());
        bike.setMileage(dto.getMileage());
        bike.setType(dto.getType());
        bike.setStock(dto.getStock());
        bike.setImageUrl(dto.getImageUrl());
        bike.setDescription(dto.getDescription());
        bike.setUpdatedBy(adminId);

        return mapToDTO(bikeRepository.save(bike));
    }

    @Override
    public void deleteBike(Long bikeId, Long adminId) {

        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        bike.setIsDeleted(true);
        bike.setUpdatedBy(adminId);

        bikeRepository.save(bike);
    }

    // ================= CUSTOMER =================

    @Override
    public List<BikeResponseDTO> getAllBikes() {
        // Return Master Bikes (Admin Data) so accurate Base Price is shown
        List<Bike> bikes = bikeRepository.findByIsDeletedFalse();

        return bikes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BikeResponseDTO> getMasterBikes() {
        List<Bike> bikes = bikeRepository.findByIsDeletedFalse();
        System.out.println("🔥 [Service] fetching Master Bikes. Found: " + bikes.size());
        return bikes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public BikeResponseDTO getBikeById(Long bikeId) {

        Bike bike = bikeRepository.findByBikeIdAndIsDeletedFalse(bikeId);

        if (bike == null) {
            throw new RuntimeException("Bike not found");
        }

        return mapToDTO(bike);
    }

    // ================= MAPPER =================

    private BikeResponseDTO mapToDTO(Bike bike) {

        BikeResponseDTO dto = new BikeResponseDTO();
        dto.setBikeId(bike.getBikeId());
        dto.setName(bike.getName());
        dto.setBrand(bike.getBrand());
        dto.setPrice(bike.getPrice());
        dto.setCc(bike.getCc());
        dto.setMileage(bike.getMileage());
        dto.setType(bike.getType());
        dto.setStock(bike.getStock());
        dto.setImageUrl(bike.getImageUrl());
        dto.setDescription(bike.getDescription());

        if (bike.getCreatedAt() != null) {
            dto.setCreatedAt(bike.getCreatedAt().toString());
        } else {
            dto.setCreatedAt("");
        }

        return dto;
    }
}
