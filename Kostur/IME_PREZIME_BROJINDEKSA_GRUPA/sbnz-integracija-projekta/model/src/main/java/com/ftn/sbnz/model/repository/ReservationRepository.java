package com.ftn.sbnz.model.repository;

import java.util.ArrayList;
import java.util.List;

import com.ftn.sbnz.model.models.Reservation;

public class ReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Reservation> findAll() {
        return new ArrayList<>();
    }

    public void save(Reservation reservation) {
    }
    
}
