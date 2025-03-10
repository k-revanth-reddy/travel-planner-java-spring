package com.sacet.travelplanner.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "destinations")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String country;
    
    private String description;
    
    private String bestTimeToVisit;
    
    @Column(columnDefinition = "TEXT")
    private String thingsToSee;
    
    private String imageUrl;
    
    @Column(nullable = false)
    private Double estimatedBudget;

    @Column(name = "trip_type")
    private String tripType; // adventure, relaxation, cultural, family

    @Column(columnDefinition = "TEXT")
    private String attractions;

    @Column(columnDefinition = "TEXT")
    private String weatherInfo;

    @Column(columnDefinition = "TEXT")
    private String travelRoutes;

    private Integer recommendedDuration; // in days
} 