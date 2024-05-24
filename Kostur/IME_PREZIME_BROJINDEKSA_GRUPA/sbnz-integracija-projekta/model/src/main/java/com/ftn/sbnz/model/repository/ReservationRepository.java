package com.ftn.sbnz.model.repository;

import java.util.ArrayList;
import java.util.List;

import com.ftn.sbnz.model.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    
}
