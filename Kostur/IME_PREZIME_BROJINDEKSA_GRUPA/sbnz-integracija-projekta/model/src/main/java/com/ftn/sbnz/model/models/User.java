package com.ftn.sbnz.model.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import enums.CleaningHabit;
import enums.Gender;
import enums.JobStatus;
import enums.Month;
import enums.PersonalityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static javax.persistence.DiscriminatorType.STRING;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType=STRING)
@DiscriminatorValue("user")
@JsonIgnoreProperties(value = {"id"})
public class User implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String username;
    private String fullName;
    private String password;
    private Gender gender;
    private LocalDate dateOfBirth;
    private boolean smoker;
    private boolean hasPets;
    private PersonalityType personalityType;
    private JobStatus jobStatus;
    private String interests;
    private CleaningHabit cleaningHabit;
    private boolean worksFromHome;
    private boolean hasCar;
    private Month moveInMonth;
    private boolean likesQuiet;
    private Integer budget;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Location> locations;
    private boolean doesntWantPets;
    private boolean dislikesSmokingIndoors;
    private boolean hasRoommate;
    private boolean blocked;
    private String photo;

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
                ", username='" + username + '\'' +
                ", fullName='" + fullName;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() { return true; }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }
}