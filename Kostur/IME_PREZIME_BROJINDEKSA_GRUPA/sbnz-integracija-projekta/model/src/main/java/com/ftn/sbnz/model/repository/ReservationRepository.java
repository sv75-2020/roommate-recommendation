package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    
}
