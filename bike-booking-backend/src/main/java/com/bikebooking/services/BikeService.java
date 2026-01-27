package com.bikebooking.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bikebooking.dto.BikeRequestDTO;
import com.bikebooking.dto.BikeResponseDTO;

public interface BikeService {

    // ADMIN
    BikeResponseDTO addBike(BikeRequestDTO dto, Long adminId);

    BikeResponseDTO updateBike(Long bikeId, BikeRequestDTO dto, Long adminId);

    void deleteBike(Long bikeId, Long adminId);

    // CUSTOMER
    List<BikeResponseDTO> getAllBikes();
    List<BikeResponseDTO> getMasterBikes();

    BikeResponseDTO getBikeById(Long bikeId);
}
