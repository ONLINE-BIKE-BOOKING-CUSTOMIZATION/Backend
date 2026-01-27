package com.bikebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bikebooking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    java.util.Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
