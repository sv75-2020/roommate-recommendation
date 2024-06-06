package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.AccommodationReview;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview, Long> {
    Optional<AccommodationReview> findByUserIdAndAccommodationId(Long userId, Long accommodationId);
}
