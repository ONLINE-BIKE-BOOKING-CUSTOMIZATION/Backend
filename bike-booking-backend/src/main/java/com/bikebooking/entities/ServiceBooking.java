package com.bikebooking.entity;

import com.bikebooking.enums.ServiceStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "service_bookings")
public class ServiceBooking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private String problemDescription;

    private String serviceDate;   // preferred date

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    // Many services belong to ONE user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many services belong to ONE bike
    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    // Dealer assigned
    @ManyToOne
    @JoinColumn(name = "dealer_id")
    private User dealer;   // role = DEALER

    // Getters & Setters

    public Long getServiceId() {
        return serviceId;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
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

    public User getDealer() {
        return dealer;
    }

    public void setDealer(User dealer) {
        this.dealer = dealer;
    }
}
