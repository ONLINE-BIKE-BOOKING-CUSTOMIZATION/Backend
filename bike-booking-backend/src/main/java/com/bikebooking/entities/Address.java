package com.bikebooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String pincode;

    // 🔥 ONE ADDRESS BELONGS TO ONE USER (Optional now)
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
