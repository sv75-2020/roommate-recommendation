package com.ftn.sbnz.model.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value = {"id"})
public class AccommodationPreferences {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private boolean closeToUni;
    private boolean closeToCenter;
    private boolean groundFloor;
    private boolean topFloor;
    private boolean elevator;
    private boolean AC;
    private Integer numOfRooms;
    @OneToOne
    private User user;
}
