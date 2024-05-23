package com.ftn.sbnz.model.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
@DiscriminatorValue("user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String email;
    private String fullName;
    private String password;
    private Gender gender;
    private LocalDate dateOfBirth;
    private boolean smoker;
    private boolean hasPets;
    private PersonalityType personalityType;
    private JobStatus jobStatus;
    private List<String> interests;
    private CleaningHabit cleaningHabit;
    private boolean worksFromHome;
    private boolean hasCar;
    private Month moveInMonth;
    private boolean likesQuiet;
    private Integer budget;
    private List<Location> locations;
    private boolean doesntWantPets;
    private boolean dislikesSmokingIndoors;
    private boolean hasRoommate;
    private boolean blocked;

    public List<String> getLocationsAddress(){
        List<String> addresses=new ArrayList<>();
        for (Location location : this.locations) {
            addresses.add(location.getAddress());
        }
    return addresses;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName;
    }

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}