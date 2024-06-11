package com.ftn.sbnz.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ftn.sbnz.model.dto.AccommodationDTO;
import com.ftn.sbnz.model.models.Accommodation;

import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("SELECT new com.ftn.sbnz.model.dto.AccommodationDTO(a) FROM Accommodation a")
    List<AccommodationDTO> findAllAccommodationDTOs();

    
    }