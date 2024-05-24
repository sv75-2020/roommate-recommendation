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
public class Accommodation {
    // Attributes
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String address;
    private Integer numOfRooms;
    private Integer price;
    private boolean petsAllowed;
    private boolean smokingAllowed;
    private boolean parking;
    private boolean fastInternet;
    @ManyToOne
    private Location location;
}
