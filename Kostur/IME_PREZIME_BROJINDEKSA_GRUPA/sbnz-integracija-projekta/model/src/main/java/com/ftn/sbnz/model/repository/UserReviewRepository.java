package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.UserReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

    @Query("SELECT AVG(ur.rating) FROM UserReview ur WHERE ur.ratedUser.id = :userId")
    Double findAverageRatingByUserId(Long userId);
}