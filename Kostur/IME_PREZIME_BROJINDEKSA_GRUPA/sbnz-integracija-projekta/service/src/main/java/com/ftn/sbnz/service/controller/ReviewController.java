package com.ftn.sbnz.service.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.dto.AccommodationDTO;
import com.ftn.sbnz.model.dto.AccommodationReviewDTO;
import com.ftn.sbnz.model.dto.UserReviewDTO;
import com.ftn.sbnz.model.models.AccommodationReview;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.models.UserReview;
import com.ftn.sbnz.service.services.ReviewService;

@RestController
public class ReviewController {

    @Autowired
    public ReviewService reviewService;

    @PostMapping(consumes = "application/json", value = "/api/rateUser")
    public ResponseEntity<UserReview> rateUser(@RequestBody UserReviewDTO reviewDTO) throws IOException {
        return reviewService.rateUser(reviewDTO);

    }

    @PostMapping(consumes = "application/json", value = "/api/rateAccommodation")
    public ResponseEntity<AccommodationReview> rateAccommodation(@RequestBody AccommodationReviewDTO reviewDTO) throws IOException {
        return reviewService.rateAccommodation(reviewDTO);

    }
}
