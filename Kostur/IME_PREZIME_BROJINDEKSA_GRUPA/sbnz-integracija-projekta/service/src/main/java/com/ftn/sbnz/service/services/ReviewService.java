package com.ftn.sbnz.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.dto.AccommodationReviewDTO;
import com.ftn.sbnz.model.dto.UserReviewDTO;
import com.ftn.sbnz.model.models.Accommodation;
import com.ftn.sbnz.model.models.AccommodationReview;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.models.UserReview;
import com.ftn.sbnz.model.repository.AccommodationRepository;
import com.ftn.sbnz.model.repository.AccommodationReviewRepository;
import com.ftn.sbnz.model.repository.UserRepository;
import com.ftn.sbnz.model.repository.UserReviewRepository;

@Service
public class ReviewService {

    @Autowired
    public AccommodationReviewRepository accommodationReviewRepository;

    @Autowired
    public UserReviewRepository userReviewRepository;

    @Autowired 
    public UserRepository userRepository;

    @Autowired
    public AccommodationRepository accommodationRepository;

    public ResponseEntity<AccommodationReview> rateAccommodation(AccommodationReviewDTO reviewDTO) {

        User user = userRepository.findById(reviewDTO.getUserId())
                                    .orElseThrow(() -> new RuntimeException("User not found"));

        Accommodation accommodation = accommodationRepository.findById(reviewDTO.getAccommodationId())
                                                                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        AccommodationReview review = new AccommodationReview();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setUser(user);
        review.setAccommodation(accommodation);

        accommodationReviewRepository.save(review);
    
        return ResponseEntity.ok(review);
    }

    public ResponseEntity<UserReview> rateUser(UserReviewDTO reviewDTO) {

        User user = userRepository.findById(reviewDTO.getUserId())
                                    .orElseThrow(() -> new RuntimeException("User not found"));

        User ratedUser = userRepository.findById(reviewDTO.getRatedUserId())
                                    .orElseThrow(() -> new RuntimeException("Rated user not found"));

        UserReview review = new UserReview();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setUser(user);
        review.setRatedUser(ratedUser);

        userReviewRepository.save(review);
    
        return ResponseEntity.ok(review);
    }

}
