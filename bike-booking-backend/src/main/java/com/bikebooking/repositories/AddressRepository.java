package com.bikebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikebooking.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
