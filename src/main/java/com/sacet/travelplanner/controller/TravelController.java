package com.sacet.travelplanner.controller;

import com.sacet.travelplanner.model.*;
import com.sacet.travelplanner.repository.*;
import com.sacet.travelplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/travel")
public class TravelController {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String travelDashboard(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        UserPreferences preferences = userPreferencesRepository.findByUser(currentUser).orElse(null);
        List<Destination> recommendations = getPersonalizedRecommendations(preferences);
        List<Hotel> featuredHotels = hotelRepository.findAll().stream()
            .sorted((h1, h2) -> h2.getRating().compareTo(h1.getRating()))
            .limit(6)
            .collect(Collectors.toList());
        List<Destination> popularDestinations = getDefaultRecommendations();

        model.addAttribute("user", currentUser);
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("preferences", preferences);
        model.addAttribute("hotels", featuredHotels);
        model.addAttribute("destinations", popularDestinations);
        return "travel-dashboard";
    }

    @GetMapping("/search")
    public String searchDestinations(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String budget,
            @RequestParam(required = false) String type,
            Model model) {
        
        List<Destination> results = destinationRepository.findAll().stream()
            .filter(d -> location == null || location.isEmpty() || 
                    d.getName().toLowerCase().contains(location.toLowerCase()) || 
                    d.getCountry().toLowerCase().contains(location.toLowerCase()))
            .filter(d -> {
                if (budget == null || budget.isEmpty()) return true;
                double budgetValue = d.getEstimatedBudget();
                return switch (budget) {
                    case "budget" -> budgetValue < 20000;
                    case "mid" -> budgetValue >= 20000 && budgetValue <= 50000;
                    case "luxury" -> budgetValue > 50000;
                    default -> true;
                };
            })
            .filter(d -> type == null || type.isEmpty() || d.getTripType().equalsIgnoreCase(type))
            .collect(Collectors.toList());

        model.addAttribute("searchResults", results);
        return "search-results";
    }

    @GetMapping("/itinerary/{id}")
    public String viewItinerary(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
            List<Itinerary> itinerary = itineraryRepository.findByDestinationIdOrderByDayNumber(id);
            
            List<Hotel> recommendedHotels = hotelRepository.findByLocation(destination.getName()).stream()
                .sorted((h1, h2) -> h2.getRating().compareTo(h1.getRating()))
                .collect(Collectors.toList());

            model.addAttribute("destination", destination);
            model.addAttribute("itinerary", itinerary);
            model.addAttribute("recommendedHotels", recommendedHotels);
            return "itinerary";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "The requested destination was not found. Please select from available destinations.");
            return "redirect:/travel";
        }
    }

    @GetMapping("/api/compare")
    @ResponseBody
    public ResponseEntity<List<Destination>> compareDestinations(@RequestParam List<Long> destinations) {
        List<Destination> comparisonList = destinationRepository.findAllById(destinations);
        return ResponseEntity.ok(comparisonList);
    }

    @GetMapping("/api/recommendations")
    @ResponseBody
    public ResponseEntity<List<Destination>> getRecommendations(
            @RequestParam(required = false) String tripType,
            @RequestParam(required = false) Double maxBudget) {
        
        List<Destination> recommendations = destinationRepository.findAll().stream()
            .filter(d -> tripType == null || tripType.isEmpty() || d.getTripType().equalsIgnoreCase(tripType))
            .filter(d -> maxBudget == null || d.getEstimatedBudget() <= maxBudget)
            .limit(6)
            .collect(Collectors.toList());

        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/hotels")
    public String listHotels(Model model) {
        List<Hotel> hotels = hotelRepository.findAll();
        model.addAttribute("hotels", hotels);
        return "hotel-list";
    }

    @PostMapping("/preferences/update")
    public String updatePreferences(
            @RequestParam(required = false) Set<String> tripTypes,
            @RequestParam(required = false) String budgetRange,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) Set<String> interests,
            RedirectAttributes redirectAttributes) {
        
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to save preferences");
            return "redirect:/login";
        }

        UserPreferences preferences = userPreferencesRepository.findByUser(currentUser)
                .orElse(new UserPreferences());

        preferences.setUser(currentUser);
        preferences.setTripTypes(tripTypes != null ? tripTypes : new HashSet<>());
        preferences.setBudgetRange(budgetRange);
        preferences.setMinDuration(minDuration);
        preferences.setMaxDuration(maxDuration);
        preferences.setInterests(interests != null ? interests : new HashSet<>());

        userPreferencesRepository.save(preferences);
        redirectAttributes.addFlashAttribute("success", "Preferences updated successfully");
        return "redirect:/travel";
    }

    private List<Destination> getPersonalizedRecommendations(UserPreferences preferences) {
        if (preferences == null) {
            return getDefaultRecommendations();
        }

        List<Destination> allDestinations = destinationRepository.findAll();
        return allDestinations.stream()
            .map(destination -> {
                // Calculate a match score for each destination
                double score = 0.0;
                
                // Trip type match (30% weight)
                if (preferences.getTripTypes().isEmpty() ||
                    preferences.getTripTypes().contains(destination.getTripType().toLowerCase())) {
                    score += 30.0;
                }

                // Budget match (25% weight)
                if (preferences.getBudgetRange() != null) {
                    double budget = destination.getEstimatedBudget();
                    boolean matchesBudget = switch (preferences.getBudgetRange()) {
                        case "budget" -> budget < 20000;
                        case "mid" -> budget >= 20000 && budget <= 50000;
                        case "luxury" -> budget > 50000;
                        default -> true;
                    };
                    if (matchesBudget) {
                        score += 25.0;
                    }
                } else {
                    score += 25.0; // No budget preference specified
                }

                // Duration match (20% weight)
                if (preferences.getMinDuration() != null && preferences.getMaxDuration() != null) {
                    int duration = destination.getRecommendedDuration();
                    if (duration >= preferences.getMinDuration() && duration <= preferences.getMaxDuration()) {
                        score += 20.0;
                    }
                } else {
                    score += 20.0; // No duration preference specified
                }

                // Interests match (25% weight)
                if (!preferences.getInterests().isEmpty()) {
                    long interestMatches = preferences.getInterests().stream()
                        .filter(interest -> 
                            destination.getDescription().toLowerCase().contains(interest.toLowerCase()) ||
                            destination.getThingsToSee().toLowerCase().contains(interest.toLowerCase())
                        )
                        .count();
                    double interestScore = (interestMatches * 25.0) / preferences.getInterests().size();
                    score += interestScore;
                } else {
                    score += 25.0; // No interests specified
                }

                return new AbstractMap.SimpleEntry<>(destination, score);
            })
            .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // Sort by score descending
            .limit(6)
            .map(AbstractMap.SimpleEntry::getKey)
            .collect(Collectors.toList());
    }

    private List<Destination> getDefaultRecommendations() {
        // Return a mix of destinations sorted by budget
        return destinationRepository.findAll().stream()
            .sorted((d1, d2) -> Double.compare(d1.getEstimatedBudget(), d2.getEstimatedBudget()))
            .limit(6)
            .collect(Collectors.toList());
    }
} 