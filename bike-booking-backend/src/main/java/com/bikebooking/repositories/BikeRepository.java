package com.bikebooking.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bikebooking.entity.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    // Only fetch NON-DELETED bikes
    List<Bike> findByIsDeletedFalse( );

    // Find ANY bike (deleted or not) to prevent duplicates
    java.util.Optional<Bike> findByNameAndBrand(String name, String brand);

    // For single bike (non deleted)
    Bike findByBikeIdAndIsDeletedFalse(Long bikeId);
}
