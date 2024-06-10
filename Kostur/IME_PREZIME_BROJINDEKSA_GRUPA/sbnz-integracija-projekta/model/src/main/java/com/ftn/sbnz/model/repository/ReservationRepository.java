package com.ftn.sbnz.model.repository;

import java.util.ArrayList;
import java.util.List;

import com.ftn.sbnz.model.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.status = 3 AND (r.roommates.roommate1.id = :userId OR r.roommates.roommate2.id = :userId)")
    List<Reservation> findNotActiveReservationsByUserId(@Param("userId") Long userId);

}
