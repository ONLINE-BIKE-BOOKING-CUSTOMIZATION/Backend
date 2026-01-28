package com.bikebooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikebooking.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUser_UserId(Long userId);
    
    Optional<Customer> findByUser(com.bikebooking.entity.User user);
}
