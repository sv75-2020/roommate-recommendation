package com.ftn.sbnz.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    public LocationRepository locationRepository;

    public ResponseEntity<List<Location>> getAllLocations(){
        return ResponseEntity.ok(locationRepository.findAll());
    }

}
