package com.ftn.sbnz.service.controller;

import java.util.List;

import com.ftn.sbnz.model.models.Accommodation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    }
}
