package com.sacet.travelplanner.service;

import com.sacet.travelplanner.model.Booking;
import com.sacet.travelplanner.model.Hotel;
import com.sacet.travelplanner.model.User;
import com.sacet.travelplanner.repository.BookingRepository;
import com.sacet.travelplanner.repository.HotelRepository;
import com.sacet.travelplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(Long userId, Long hotelId, String roomType, 
                               LocalDate checkIn, LocalDate checkOut, 
                               Integer guests, Integer numberOfRooms, Boolean breakfast, 
                               String specialRequests) {
        
        // Validate dates
        if (checkIn.isBefore(LocalDate.now()) || checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Invalid dates selected");
        }

        // Validate guests per room
        if (guests > numberOfRooms * 2) {
            throw new IllegalArgumentException("Maximum 2 guests are allowed per room");
        }

        // Check room availability
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(hotelId, checkIn, checkOut);
        if (!overlappingBookings.isEmpty()) {
            throw new IllegalStateException("Room not available for selected dates");
        }

        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Calculate total price
        long numberOfNights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double totalPrice = hotel.getPricePerNight() * numberOfNights * numberOfRooms;
        if (breakfast) {
            totalPrice += 500 * numberOfNights * guests; // Breakfast cost per person per day
        }

        Booking booking = new Booking();
        booking.setHotel(hotel);
        booking.setUser(user);
        booking.setRoomType(roomType);
        booking.setNumberOfRooms(numberOfRooms);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setNumberOfGuests(guests);
        booking.setBreakfast(breakfast);
        booking.setSpecialRequests(specialRequests);
        booking.setTotalPrice(totalPrice);
        booking.setBookingStatus("PENDING");
        booking.setPaymentStatus("PENDING");

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getHotelBookings(Long hotelId) {
        return bookingRepository.findByHotelId(hotelId);
    }

    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        
        booking.setBookingStatus(status);
        return bookingRepository.save(booking);
    }

    public Booking updatePaymentStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        
        booking.setPaymentStatus(status);
        if ("PAID".equals(status)) {
            booking.setBookingStatus("CONFIRMED");
        }
        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        
        if ("CONFIRMED".equals(booking.getBookingStatus())) {
            // Add refund logic here if needed
            booking.setBookingStatus("CANCELLED");
            bookingRepository.save(booking);
        } else {
            throw new IllegalStateException("Booking cannot be cancelled");
        }
    }

    public boolean isRoomAvailable(Long hotelId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(hotelId, checkIn, checkOut);
        return overlappingBookings.isEmpty();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
} 