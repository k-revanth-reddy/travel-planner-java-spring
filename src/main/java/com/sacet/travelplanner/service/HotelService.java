package com.sacet.travelplanner.service;

import com.sacet.travelplanner.model.Hotel;
import com.sacet.travelplanner.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }
} 