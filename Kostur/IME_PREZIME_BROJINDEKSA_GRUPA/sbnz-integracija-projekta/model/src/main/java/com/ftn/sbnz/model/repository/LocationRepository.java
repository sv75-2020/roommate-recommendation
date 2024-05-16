package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    
    
}