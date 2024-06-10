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
    private String jobStatus;
    private String photo;

    public UserDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.username = user.getUsername();
        this.gender = String.valueOf(user.getGender());
        this.dateOfBirth = user.getDateOfBirth();
        this.jobStatus = String.valueOf(user.getJobStatus());
        this.photo = user.getPhoto();
    }

    // Getters and setters for all attributes
}

