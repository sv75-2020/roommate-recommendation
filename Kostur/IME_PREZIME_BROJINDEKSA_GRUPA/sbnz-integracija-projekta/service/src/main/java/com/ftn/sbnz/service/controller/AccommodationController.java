package com.ftn.sbnz.service.controller;

import java.io.IOException;
import java.util.List;

import com.ftn.sbnz.model.models.Accommodation;
import com.ftn.sbnz.model.models.AccommodationPreferences;
import com.ftn.sbnz.model.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/api/accommodations/history")
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsHistory() {
        return accommodationService.getHistoryAccommodations();

    @PostMapping(consumes = "application/json", value = "/api/addAccommodationPreferences")
    public ResponseEntity<AccommodationPreferences> addAccommodationPreferences(@RequestBody AccommodationPreferences accommodationPreferences) throws IOException {
        return accommodationService.saveAccommodationPreferences(accommodationPreferences);


    }
}
