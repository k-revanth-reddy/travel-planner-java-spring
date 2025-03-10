package com.sacet.travelplanner.repository;

import com.sacet.travelplanner.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
    List<Itinerary> findByDestinationIdOrderByDayNumber(Long destinationId);
} 