package com.ftn.sbnz.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.dto.AccommodationDTO;
import com.ftn.sbnz.service.services.AccommodationService;

@RestController
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(value = "/api/accommodations")
    public ResponseEntity<List<AccommodationDTO>> getAllAccomodations() {
        return accommodationService.getAllAccommodations();

    }
}
