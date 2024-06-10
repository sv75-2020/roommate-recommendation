package com.ftn.sbnz.service.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AccommodationReviewDTO> rateAccommodation(@RequestBody AccommodationReviewDTO reviewDTO) throws IOException {
        return reviewService.rateAccommodation(reviewDTO);

    }

    @GetMapping("/api/review/{accommodationId}/accommodation")
    public ResponseEntity<AccommodationReviewDTO> getReviewForAccommodation(@PathVariable Long accommodationId) {
        AccommodationReview review = reviewService.getReviewForAccommodation(accommodationId);
        if (review != null) {
            return new ResponseEntity<>(new AccommodationReviewDTO(review), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/average/{accommodationId}/accommodation")
    public ResponseEntity<String> getAverageRatingAccommodation(@PathVariable Long accommodationId) {
        Double rating = reviewService.getAverageRatingForAccommodation(accommodationId);
        return ResponseEntity.ok(rating.toString());
    }

    @GetMapping("/api/average/{userId}/user")
    public ResponseEntity<String> getAverageRatingUser(@PathVariable Long userId) {
        Double rating = reviewService.getAverageRatingForUser(userId);
        return ResponseEntity.ok(rating.toString());
    }
}
