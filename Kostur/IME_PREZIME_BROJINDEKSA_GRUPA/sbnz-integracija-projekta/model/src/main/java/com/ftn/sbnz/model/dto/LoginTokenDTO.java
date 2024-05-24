package com.ftn.sbnz.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginTokenDTO {

    private String accessToken;
    private String refreshToken;

}
