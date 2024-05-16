package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    
    
    }