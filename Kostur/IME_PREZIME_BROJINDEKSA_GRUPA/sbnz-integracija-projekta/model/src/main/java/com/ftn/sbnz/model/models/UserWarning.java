package com.ftn.sbnz.model.models;


import java.time.LocalDate;

import javax.persistence.*;

import enums.CleaningHabit;
import enums.Gender;
import enums.JobStatus;
import enums.Month;
import enums.PersonalityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserWarning {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    private User user;
    private LocalDate date;
}
