package com.bikebooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikebooking.entity.Dealer;
import com.bikebooking.entity.User;

public interface DealerRepository extends JpaRepository<Dealer, Long> {

    Optional<Dealer> findByUser_UserId(Long userId);

    Optional<Dealer> findByShowroomName(String showroomName);

    // All pending (not verified) dealers
    List<Dealer> findByVerifiedFalse();

    Optional<Dealer> findByDealerIdAndVerifiedFalse(Long dealerId);
    

    Optional<Dealer> findByUser(User user);
    
}
