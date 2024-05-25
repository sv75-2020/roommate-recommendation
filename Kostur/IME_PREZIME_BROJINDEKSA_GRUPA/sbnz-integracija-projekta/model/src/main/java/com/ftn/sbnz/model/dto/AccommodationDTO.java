package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.models.Accommodation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {
    private String address;
    private Integer price;

    public AccommodationDTO(Accommodation a){
        this.address=a.getAddress();
        this.price=a.getPrice();
    }

}
