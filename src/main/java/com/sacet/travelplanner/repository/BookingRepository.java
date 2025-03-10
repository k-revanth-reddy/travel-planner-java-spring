package com.sacet.travelplanner.repository;

import com.sacet.travelplanner.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByHotelId(Long hotelId);
    
    @Query("SELECT b FROM Booking b WHERE b.hotel.id = :hotelId AND " +
           "((b.checkInDate BETWEEN :checkIn AND :checkOut) OR " +
           "(b.checkOutDate BETWEEN :checkIn AND :checkOut))")
    List<Booking> findOverlappingBookings(
        @Param("hotelId") Long hotelId,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );

    List<Booking> findByBookingStatus(String status);
    List<Booking> findByPaymentStatus(String status);
} 