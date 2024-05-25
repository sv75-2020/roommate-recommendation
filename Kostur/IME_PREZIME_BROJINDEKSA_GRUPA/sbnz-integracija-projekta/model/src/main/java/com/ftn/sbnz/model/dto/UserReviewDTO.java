package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.models.UserReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReviewDTO {
    private Double rating;
    private String comment;
    private Long userId;
    private Long ratedUserId;

    public UserReviewDTO(UserReview review){
        this.rating=review.getRating();
        this.comment=review.getComment();
        this.userId=review.getUser().getId();
        this.ratedUserId=review.getRatedUser().getId();
    }

}