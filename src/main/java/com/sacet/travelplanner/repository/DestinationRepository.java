package com.sacet.travelplanner.repository;

import com.sacet.travelplanner.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByCountry(String country);
    List<Destination> findByEstimatedBudgetLessThanEqual(Double budget);
} 