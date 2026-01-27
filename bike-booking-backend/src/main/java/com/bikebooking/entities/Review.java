package com.bikebooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Integer rating;   // 1 to 5

    private String comment;

    // Many reviews belong to ONE user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many reviews belong to ONE bike
    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    // Getters & Setters

    public Long getReviewId() {
        return reviewId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
