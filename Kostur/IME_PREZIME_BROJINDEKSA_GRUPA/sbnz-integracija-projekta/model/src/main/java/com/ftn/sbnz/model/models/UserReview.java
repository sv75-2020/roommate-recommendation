package com.ftn.sbnz.model.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_review")
public class UserReview {
    // Attributes
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Double rating;
    private String comment;
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = "user_gives_rating_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;

}
