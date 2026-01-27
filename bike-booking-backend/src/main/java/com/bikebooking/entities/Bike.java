package com.bikebooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bikes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bikeId;

    private String name;
    private String brand;
    private Double price;
    private Integer cc;
    private String mileage;
    private String type;     // Sports, Cruiser, Electric
    private Integer stock;
    
    @Column(length = 1000)
    private String description;
    
    private String imageUrl;
    
}
