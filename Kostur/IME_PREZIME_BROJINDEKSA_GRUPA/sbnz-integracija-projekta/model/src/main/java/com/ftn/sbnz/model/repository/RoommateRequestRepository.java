package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.RoommateRequest;

import java.util.List;

@Repository
public interface RoommateRequestRepository extends JpaRepository<RoommateRequest, Long>{
    @Query("SELECT r FROM RoommateRequest r WHERE (r.userId = :userId OR r.requestedUserId = :userId) AND r.status=1")
    List<RoommateRequest> findByUserIdOrRequestedUserId(@Param("userId") Long userId);
}
