package com.bikebooking.entity;

import java.time.LocalDateTime;

import com.bikebooking.enums.PaymentType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    private Double amount;
    
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; // ADVANCE, FULL, etc.

    private String razorpayPaymentId;
    
    private String paymentMethod; // CARD, UPI, etc.
    
    private LocalDateTime timestamp;
    
    private String status; // SUCCESS, FAILED
}
