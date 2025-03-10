package com.sacet.travelplanner.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String location;
    
    private String description;
    
    @Column(nullable = false)
    private Double pricePerNight;
    
    @Column(nullable = false)
    private Double rating;
    
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String amenities;

    @Column(name = "room_types", columnDefinition = "TEXT")
    private String roomTypes;

    private Boolean hasWifi;

    private Boolean hasParking;

    private Boolean hasPool;

    private Boolean hasRestaurant;

    @Column(name = "check_in_time")
    private String checkInTime;

    @Column(name = "check_out_time")
    private String checkOutTime;

    @Column(columnDefinition = "TEXT")
    private String policies;

    @Column(name = "cancellation_policy", columnDefinition = "TEXT")
    private String cancellationPolicy;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
} 