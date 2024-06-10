package com.ftn.sbnz.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public ResponseEntity<AccommodationReviewDTO> rateAccommodation(AccommodationReviewDTO reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Accommodation accommodation = accommodationRepository.findById(reviewDTO.getAccommodationId())
                                                                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        AccommodationReview review = new AccommodationReview();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setUser(user);
        review.setAccommodation(accommodation);

        accommodationReviewRepository.save(review);
    
        return ResponseEntity.ok(reviewDTO);
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

    public AccommodationReview getReviewForAccommodation(Long accommodationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(accommodationReviewRepository.findByUserIdAndAccommodationId(user.getId(), accommodationId));
        return accommodationReviewRepository.findByUserIdAndAccommodationId(user.getId(), accommodationId)
                .orElse(null);

    }

    public Double getAverageRatingForAccommodation(Long accommodationId) {
        return accommodationReviewRepository.findAverageRatingByAccommodationId(accommodationId);
    }

    public Double getAverageRatingForUser(Long userId) {
        return userReviewRepository.findAverageRatingByUserId(userId);
    }
}
