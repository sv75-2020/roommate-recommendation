package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.models.Accommodation;

import com.ftn.sbnz.model.models.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {
    private Long id;
    private String address;
    private Integer price;
    private int numOfRooms;
    private Location location;

    public AccommodationDTO(Accommodation a){
        this.address=a.getAddress();
        this.price=a.getPrice();
        this.numOfRooms = a.getNumOfRooms();
        this.id = a.getId();
        this.location = a.getLocation();

    }

}
