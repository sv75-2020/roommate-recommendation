package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.models.AccommodationReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationReviewDTO {
    private Double rating;
    private String comment;
    private Long userId;
    private Long accommodationId;

    public AccommodationReviewDTO(AccommodationReview review){
        this.rating=review.getRating();
        this.comment=review.getComment();
        this.userId=review.getUser().getId();
        this.accommodationId=review.getAccommodation().getId();
    }

}