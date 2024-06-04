package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccommodationRequirements {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    private Roommates roommates;
    private Integer combinedBudget;
    private boolean petsAllowed;
    private boolean smokingAllowed;
    private boolean fastInternet;
    @ManyToMany
    private List<Location> combinedLocation;

}
