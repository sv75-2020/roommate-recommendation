package com.ftn.sbnz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PopularLocationsDTO {
    private String locationName;
    private Long count;
}
