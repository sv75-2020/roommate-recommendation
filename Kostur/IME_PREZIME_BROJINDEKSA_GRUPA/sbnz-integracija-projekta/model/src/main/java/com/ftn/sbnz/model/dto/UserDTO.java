package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String gender;
    private LocalDate dateOfBirth;
    private boolean smoker;
    private boolean hasPets;
    private String jobStatus;
    private Integer budget;

    public UserDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.username = user.getUsername();
        this.gender = String.valueOf(user.getGender());
        this.dateOfBirth = user.getDateOfBirth();
        this.smoker = user.isSmoker();
        this.hasPets = user.isHasPets();
        this.jobStatus = String.valueOf(user.getJobStatus());
        this.budget = user.getBudget();
    }

    // Getters and setters for all attributes
}

