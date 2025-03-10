package com.sacet.travelplanner.controller;

import com.sacet.travelplanner.model.Hotel;
import com.sacet.travelplanner.model.Destination;
import com.sacet.travelplanner.repository.HotelRepository;
import com.sacet.travelplanner.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @PostMapping("/hotels")
    public String addHotel(@ModelAttribute Hotel hotel, RedirectAttributes redirectAttributes) {
        try {
            hotelRepository.save(hotel);
            redirectAttributes.addFlashAttribute("success", "Hotel added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add hotel: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/hotels/{id}")
    @ResponseBody
    public ResponseEntity<Hotel> getHotel(@PathVariable Long id) {
        return hotelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/hotels/{id}/edit")
    public String editHotel(@PathVariable Long id, @ModelAttribute Hotel hotel, RedirectAttributes redirectAttributes) {
        try {
            hotel.setId(id);
            hotelRepository.save(hotel);
            redirectAttributes.addFlashAttribute("success", "Hotel updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update hotel: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/hotels/{id}/delete")
    public String deleteHotel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            hotelRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Hotel deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete hotel: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/destinations")
    public String addDestination(@ModelAttribute Destination destination, RedirectAttributes redirectAttributes) {
        try {
            destinationRepository.save(destination);
            redirectAttributes.addFlashAttribute("success", "Destination added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add destination: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/destinations/{id}")
    @ResponseBody
    public ResponseEntity<Destination> getDestination(@PathVariable Long id) {
        return destinationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/destinations/{id}/edit")
    public String editDestination(@PathVariable Long id, @ModelAttribute Destination destination, RedirectAttributes redirectAttributes) {
        try {
            destination.setId(id);
            destinationRepository.save(destination);
            redirectAttributes.addFlashAttribute("success", "Destination updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update destination: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/destinations/{id}/delete")
    public String deleteDestination(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            destinationRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Destination deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete destination: " + e.getMessage());
        }
        return "redirect:/admin";
    }
} 