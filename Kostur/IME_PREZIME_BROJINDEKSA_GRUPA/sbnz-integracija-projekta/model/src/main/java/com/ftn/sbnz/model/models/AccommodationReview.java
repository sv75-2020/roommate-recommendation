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
public class AccommodationReview {
    // Attributes
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Double rating;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    private Accommodation accommodation;

}
