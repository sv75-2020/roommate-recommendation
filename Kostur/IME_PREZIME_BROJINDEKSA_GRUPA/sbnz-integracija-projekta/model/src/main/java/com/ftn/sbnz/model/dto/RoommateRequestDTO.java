package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.models.RoommateRequest;

import enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoommateRequestDTO {
    private RequestStatus status;
    private Long userId;
    private Long requestedUserId;

    public RoommateRequestDTO(RoommateRequest request){
        this.status=request.getStatus();
        this.userId=request.getUser().getId();
        this.requestedUserId=request.getRequestedUser().getId();
    }

}
