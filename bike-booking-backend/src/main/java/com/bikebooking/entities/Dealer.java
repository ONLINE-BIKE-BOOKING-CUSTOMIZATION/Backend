package com.bikebooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dealers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dealer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dealerId;

    // Login account
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Showroom identity
    @Column(nullable = false, unique = true)
    private String showroomName;

    private String gstNumber;
    private String contactNumber;

    // Admin approval
    @Column(nullable = false)
    private Boolean verified = false;

    // Showroom address
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
