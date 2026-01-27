package com.bikebooking.entity;

import java.time.LocalDate;

import com.bikebooking.enums.BookingStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    // Customer who booked
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Dealer showroom
    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = false)
    private Dealer dealer;

    // Bike model
    @ManyToOne
    @JoinColumn(name = "bike_id", nullable = false)
    private Bike bike;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDate deliveryDate;

    private double totalAmount;

}
