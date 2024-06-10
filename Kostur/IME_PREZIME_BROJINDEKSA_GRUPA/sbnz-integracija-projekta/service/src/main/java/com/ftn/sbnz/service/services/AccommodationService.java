package com.ftn.sbnz.service.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ftn.sbnz.model.models.Accommodation;
import com.ftn.sbnz.model.models.Reservation;
import com.ftn.sbnz.model.models.User;
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
    public ReservationService reservationService;
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

    public ResponseEntity<List<AccommodationDTO>> getHistoryAccommodations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Reservation> notActiveReservations = reservationService.getNotActive(user.getId());
        List<Accommodation> accommodations = notActiveReservations.stream()
                .map(Reservation::getAccommodation)
                .collect(Collectors.toList());

        // Convert list of accommodations to list of AccommodationDTO objects
        List<AccommodationDTO> accommodationDTOs = accommodations.stream()
                .map(AccommodationDTO::new) // Assuming AccommodationDTO constructor is available
                .collect(Collectors.toList());

        // Return the list of AccommodationDTOs within a ResponseEntity
        return ResponseEntity.ok(accommodationDTOs);
    }

      public ResponseEntity<AccommodationPreferences> saveAccommodationPreferences(AccommodationPreferences accommodationPreferences) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user= (User) userRepository.findByUsername(username);

        accommodationPreferences.setUser(user);

        return ResponseEntity.ok(accommodationPreferencesRepository.save(accommodationPreferences));
    }
}
