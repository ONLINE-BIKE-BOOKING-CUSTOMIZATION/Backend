package com.bikebooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dealer_bikes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealerBike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dealerBikeId;

    // Which showroom
    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = false)
    private Dealer dealer;

    // Which bike model
    @ManyToOne
    @JoinColumn(name = "bike_id", nullable = false)
    private Bike bike;

    // Dealer specific price
    private double price;

    // Stock in showroom
    private int stock;

    // Offer text
    private String offer;

    // Availability
    private boolean available = true;
}
