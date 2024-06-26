package com.ftn.sbnz.service.controller;

import java.io.IOException;
import java.util.List;

import com.ftn.sbnz.model.dto.RoommateRequestDTO;
import com.ftn.sbnz.model.models.Accommodation;
import com.ftn.sbnz.model.models.AccommodationPreferences;
import com.ftn.sbnz.model.models.User;
import enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ftn.sbnz.model.dto.AccommodationDTO;
import com.ftn.sbnz.service.services.AccommodationService;

@RestController
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(value = "/api/accommodations")
    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations() {
        return accommodationService.getAllAccommodations();

    }
    @GetMapping(value = "/api/accommodations/{id}")
    public ResponseEntity<Accommodation> getAccommodationById(@PathVariable Long id) {
        return accommodationService.getAccommodationById(id);
    }

    @GetMapping(value = "/api/activeAccommodation/{id}")
    public ResponseEntity<Accommodation> getActiveAccommodation(@PathVariable Long id) {
        return accommodationService.getActiveAccommodation(id);
    }

    @GetMapping(value = "/api/accommodations/history")
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsHistory() {
        return accommodationService.getHistoryAccommodations();
    }

    @PostMapping(consumes = "application/json", value = "/api/addAccommodationPreferences")
    public ResponseEntity<AccommodationPreferences> addAccommodationPreferences(@RequestBody AccommodationPreferences accommodationPreferences) throws IOException {
        return accommodationService.saveAccommodationPreferences(accommodationPreferences);
    }

    @GetMapping(value = "/api/findAccommodation")
    public ResponseEntity<AccommodationDTO> findAccommodation() throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Accommodation accommodation=accommodationService.findRecommendedAccommodation();
        if (accommodation != null) {
            return ResponseEntity.ok(new AccommodationDTO(accommodation));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
