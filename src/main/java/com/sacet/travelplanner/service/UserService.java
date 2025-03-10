package com.sacet.travelplanner.service;

import com.sacet.travelplanner.model.User;
import com.sacet.travelplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    public User getCurrentUser() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId).orElse(null);
    }

    public void setCurrentUser(User user) {
        if (user != null) {
            httpSession.setAttribute("userId", user.getId());
            httpSession.setAttribute("user", user);
        } else {
            httpSession.removeAttribute("userId");
            httpSession.removeAttribute("user");
        }
    }

    public void logout() {
        httpSession.removeAttribute("userId");
        httpSession.removeAttribute("user");
    }

    public User authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (password.equals(user.getPassword())) { // In a real app, use password hashing
                setCurrentUser(user);
                return user;
            }
        }
        return null;
    }

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        // In a real app, hash the password before saving
        return userRepository.save(user);
    }

    public boolean isAuthenticated() {
        return getCurrentUser() != null;
    }
} 