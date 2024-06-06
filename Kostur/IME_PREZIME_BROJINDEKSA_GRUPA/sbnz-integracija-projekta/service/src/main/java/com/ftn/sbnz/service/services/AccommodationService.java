package com.ftn.sbnz.service.services;

import java.util.List;
import java.util.Optional;

import com.ftn.sbnz.model.models.Accommodation;
import com.ftn.sbnz.model.models.AccommodationPreferences;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.repository.AccommodationPreferencesRepository;
import com.ftn.sbnz.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.dto.AccommodationDTO;
import com.ftn.sbnz.model.dto.UserDTO;
import com.ftn.sbnz.model.repository.AccommodationRepository;

@Service
public class AccommodationService {

    @Autowired
    public AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationPreferencesRepository accommodationPreferencesRepository;

    @Autowired
    public UserRepository userRepository;

    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations() {
        List<AccommodationDTO> accommodationDTOs = accommodationRepository.findAllAccommodationDTOs();
        return ResponseEntity.ok(accommodationDTOs);
    }

    public ResponseEntity<Accommodation> getAccommodationById(Long id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        return accommodation.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<AccommodationPreferences> saveAccommodationPreferences(AccommodationPreferences accommodationPreferences) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user= (User) userRepository.findByUsername(username);

        accommodationPreferences.setUser(user);

        return ResponseEntity.ok(accommodationPreferencesRepository.save(accommodationPreferences));
    }
}
