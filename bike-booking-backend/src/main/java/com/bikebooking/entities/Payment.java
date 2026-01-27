package com.bikebooking.entity;

import com.bikebooking.enums.PaymentStatus;
import com.bikebooking.enums.PaymentType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;   // ADVANCE / FULL / FINAL

    private double paidAmount;
    private double remainingAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // Summary Fields (Updated by Service)
    private String lastTransactionId;
    private String lastPaymentMethod; 

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<PaymentTransaction> transactions;
}
