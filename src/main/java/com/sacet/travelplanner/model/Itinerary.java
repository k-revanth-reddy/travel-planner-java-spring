package com.sacet.travelplanner.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "itineraries")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(columnDefinition = "TEXT")
    private String morningActivities;

    @Column(columnDefinition = "TEXT")
    private String afternoonActivities;

    @Column(columnDefinition = "TEXT")
    private String eveningActivities;

    private String suggestedAccommodation;

    @Column(columnDefinition = "TEXT")
    private String transportationDetails;

    @Column(columnDefinition = "TEXT")
    private String mealSuggestions;

    private Double estimatedDailyCost;
} 