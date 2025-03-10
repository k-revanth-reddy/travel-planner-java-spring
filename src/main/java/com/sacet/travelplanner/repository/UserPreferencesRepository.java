package com.sacet.travelplanner.repository;

import com.sacet.travelplanner.model.UserPreferences;
import com.sacet.travelplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
    Optional<UserPreferences> findByUser(User user);
} 