package com.ftn.sbnz.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
