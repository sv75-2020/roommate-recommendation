package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.UserReview;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
    
    
}