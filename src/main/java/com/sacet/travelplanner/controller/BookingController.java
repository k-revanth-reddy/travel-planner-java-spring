package com.sacet.travelplanner.controller;

import com.sacet.travelplanner.model.Booking;
import com.sacet.travelplanner.model.Hotel;
import com.sacet.travelplanner.model.User;
import com.sacet.travelplanner.service.BookingService;
import com.sacet.travelplanner.service.HotelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotel/{hotelId}")
    public String showBookingForm(@PathVariable Long hotelId, Model model, HttpSession session) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Get hotel and handle not found case
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("hotel", hotel);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("user", user);
        model.addAttribute("minDate", LocalDate.now());
        model.addAttribute("maxDate", LocalDate.now().plusYears(1));
        
        // Safely handle room types
        if (hotel.getRoomTypes() != null && !hotel.getRoomTypes().isEmpty()) {
            String[] roomTypes = hotel.getRoomTypes().split(", ");
            model.addAttribute("roomTypes", roomTypes);
        } else {
            model.addAttribute("roomTypes", new String[]{"Standard Room"}); // Default room type
        }

        return "booking-form";
    }

    @PostMapping("/create")
    public String createBooking(
            @RequestParam Long userId,
            @RequestParam Long hotelId,
            @RequestParam String roomType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam Integer guests,
            @RequestParam Integer numberOfRooms,
            @RequestParam(defaultValue = "false") Boolean breakfast,
            @RequestParam(required = false) String specialRequests,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Validate guests per room
            if (guests > numberOfRooms * 2) {
                throw new IllegalArgumentException("Maximum 2 guests are allowed per room. Please add more rooms or reduce the number of guests.");
            }

            Booking booking = bookingService.createBooking(
                userId, hotelId, roomType, checkIn, checkOut,
                guests, numberOfRooms, breakfast, specialRequests
            );
            redirectAttributes.addFlashAttribute("success", 
                "Booking created successfully! Your booking ID is: " + booking.getId());
            return "redirect:/booking/payment/" + booking.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/booking/hotel/" + hotelId;
        }
    }

    @GetMapping("/payment/{bookingId}")
    public String showPaymentPage(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("booking", booking);
        model.addAttribute("bookingId", bookingId);
        return "payment";
    }

    @PostMapping("/payment/{bookingId}/process")
    public String processPayment(
            @PathVariable Long bookingId,
            @RequestParam String paymentMethod,
            RedirectAttributes redirectAttributes) {
        
        try {
            Booking booking = bookingService.updatePaymentStatus(bookingId, "PAID");
            redirectAttributes.addFlashAttribute("success", 
                "Payment successful! Your booking is confirmed.");
            return "redirect:/booking/confirmation/" + booking.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Payment failed: " + e.getMessage());
            return "redirect:/booking/payment/" + bookingId;
        }
    }

    @GetMapping("/confirmation/{bookingId}")
    public String showConfirmation(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("booking", booking);
        return "booking-confirmation";
    }

    @GetMapping("/user/{userId}")
    public String getUserBookings(@PathVariable Long userId, Model model, HttpSession session) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Only allow users to view their own bookings
        if (!user.getId().equals(userId)) {
            return "redirect:/dashboard";
        }

        List<Booking> bookings = bookingService.getUserBookings(userId);
        model.addAttribute("bookings", bookings);
        model.addAttribute("user", user);
        return "user-bookings";
    }

    @PostMapping("/{bookingId}/cancel")
    public String cancelBooking(
            @PathVariable Long bookingId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            bookingService.cancelBooking(bookingId);
            redirectAttributes.addFlashAttribute("success", "Booking cancelled successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not cancel booking: " + e.getMessage());
        }
        return "redirect:/booking/user/" + user.getId();
    }

    @GetMapping("/check-availability")
    @ResponseBody
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        
        boolean isAvailable = bookingService.isRoomAvailable(hotelId, checkIn, checkOut);
        return ResponseEntity.ok(isAvailable);
    }
} 