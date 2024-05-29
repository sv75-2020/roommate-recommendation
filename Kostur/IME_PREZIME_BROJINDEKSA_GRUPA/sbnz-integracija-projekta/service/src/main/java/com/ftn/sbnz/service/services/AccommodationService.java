package com.ftn.sbnz.service.services;

import java.util.List;
import java.util.Optional;

import com.ftn.sbnz.model.models.Accommodation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.dto.AccommodationDTO;
import com.ftn.sbnz.model.dto.UserDTO;
import com.ftn.sbnz.model.repository.AccommodationRepository;

@Service
public class AccommodationService {

    @Autowired
    public AccommodationRepository accommodationRepository;

    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations() {
        List<AccommodationDTO> accommodationDTOs = accommodationRepository.findAllAccommodationDTOs();
        return ResponseEntity.ok(accommodationDTOs);
    }

    public ResponseEntity<Accommodation> getAccommodationById(Long id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        return accommodation.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
