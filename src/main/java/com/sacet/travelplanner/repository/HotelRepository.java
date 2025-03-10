package com.sacet.travelplanner.repository;

import com.sacet.travelplanner.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByLocation(String location);
    List<Hotel> findByPricePerNightLessThanEqual(Double price);
} 