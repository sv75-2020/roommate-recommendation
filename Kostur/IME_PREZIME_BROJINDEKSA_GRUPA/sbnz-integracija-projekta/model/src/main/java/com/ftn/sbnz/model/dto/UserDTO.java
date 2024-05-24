package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    private String username;

    public UserDTO(User user) {
        this.fullName = user.getFullName();
        this.username = user.getUsername();
    }
}
