package com.bikebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter

@Setter

@ToString

@EqualsAndHashCode

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class BikeResponseDTO {

    private Long bikeId;
    private String name;
    private String brand;
    private Double price;
    private Integer cc;
    private String mileage;
    private String type;
    private Integer stock;
    private String imageUrl;
    private String description;
    private Long dealerId;
    private String showroomName;

    private String createdAt;

    // getters setters
}
