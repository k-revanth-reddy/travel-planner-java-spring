package com.sacet.travelplanner.controller;

import com.sacet.travelplanner.model.User;
import com.sacet.travelplanner.model.Booking;
import com.sacet.travelplanner.model.Itinerary;
import com.sacet.travelplanner.repository.UserRepository;
import com.sacet.travelplanner.repository.HotelRepository;
import com.sacet.travelplanner.repository.DestinationRepository;
import com.sacet.travelplanner.repository.BookingRepository;
import com.sacet.travelplanner.repository.ItineraryRepository;
import com.sacet.travelplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        }
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "redirect:/signup?error=email";
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            return "redirect:/signup?error=username";
        }
        user.setRole("USER"); // Set default role
        User savedUser = userRepository.save(user);
        userService.setCurrentUser(savedUser); // Set the user in session
        return "redirect:/login?success";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
        
        if (user == null) {
            return "redirect:/login?error";
        }
        
        userService.setCurrentUser(user); // Use UserService instead of session directly
        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin";
        }
        return "redirect:/travel"; // Redirect to travel dashboard
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        User user = userService.getCurrentUser();
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
        model.addAttribute("hotels", hotelRepository.findAll());
        model.addAttribute("destinations", destinationRepository.findAll());
        model.addAttribute("bookings", bookingRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("itineraries", itineraryRepository.findAll());
        return "admin";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "redirect:/travel"; // Redirect to travel dashboard
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/";
    }

    @GetMapping("/admin/itineraries/{id}")
    @ResponseBody
    public Itinerary getItinerary(@PathVariable Long id) {
        return itineraryRepository.findById(id).orElse(null);
    }

    @PostMapping("/admin/itineraries")
    public String addItinerary(@ModelAttribute Itinerary itinerary, @RequestParam Long destinationId) {
        itinerary.setDestination(destinationRepository.findById(destinationId).orElse(null));
        itineraryRepository.save(itinerary);
        return "redirect:/admin";
    }

    @PostMapping("/admin/itineraries/{id}/edit")
    public String editItinerary(@PathVariable Long id, @ModelAttribute Itinerary itinerary, @RequestParam Long destinationId) {
        Itinerary existingItinerary = itineraryRepository.findById(id).orElse(null);
        if (existingItinerary != null) {
            existingItinerary.setDestination(destinationRepository.findById(destinationId).orElse(null));
            existingItinerary.setDayNumber(itinerary.getDayNumber());
            existingItinerary.setMorningActivities(itinerary.getMorningActivities());
            existingItinerary.setAfternoonActivities(itinerary.getAfternoonActivities());
            existingItinerary.setEveningActivities(itinerary.getEveningActivities());
            existingItinerary.setSuggestedAccommodation(itinerary.getSuggestedAccommodation());
            existingItinerary.setTransportationDetails(itinerary.getTransportationDetails());
            existingItinerary.setMealSuggestions(itinerary.getMealSuggestions());
            existingItinerary.setEstimatedDailyCost(itinerary.getEstimatedDailyCost());
            itineraryRepository.save(existingItinerary);
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/itineraries/{id}/delete")
    public String deleteItinerary(@PathVariable Long id) {
        itineraryRepository.deleteById(id);
        return "redirect:/admin";
    }
} 