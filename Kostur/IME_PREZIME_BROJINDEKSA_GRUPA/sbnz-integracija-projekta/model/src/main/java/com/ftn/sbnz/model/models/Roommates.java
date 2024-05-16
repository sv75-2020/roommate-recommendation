package com.ftn.sbnz.model.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Roommates {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private User roommate1;
    private User roommate2;
    private boolean accepted;
}
