package com.bikebooking.dto;

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
public class BikeRequestDTO {

    private String name;
    private String brand;
    private Double price;
    private Integer cc;
    private String mileage;
    private String type;
    private Integer stock;
    private String imageUrl;
    private String description;

    // getters setters
}
