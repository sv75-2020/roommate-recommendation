package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.models.RoommateRequest;
import com.ftn.sbnz.model.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Roommates;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoommatesRepository extends JpaRepository<Roommates, Long> {

    @Query("SELECT r FROM Roommates r WHERE (r.roommate1.id = :userId1 AND r.roommate2.id = :userId2) OR (r.roommate2.id = :userId1 AND r.roommate1.id = :userId2) ")
    Roommates findRoommatesFromRequest(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

}
