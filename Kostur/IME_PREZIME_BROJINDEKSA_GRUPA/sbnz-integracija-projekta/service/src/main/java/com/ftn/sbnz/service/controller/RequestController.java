package com.ftn.sbnz.service.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.dto.RoommateRequestDTO;
import com.ftn.sbnz.model.models.RoommateRequest;
import com.ftn.sbnz.model.models.Roommates;
import com.ftn.sbnz.service.services.RequestService;

@RestController
public class RequestController {

    @Autowired
    public RequestService requestService;

    
    @PostMapping(consumes = "application/json", value = "/api/requestRoommate")
    public ResponseEntity<RoommateRequest> requestRoommate(@RequestBody RoommateRequestDTO requestDTO) throws IOException {
        return requestService.sendRoommateRequest(requestDTO);

    }

    @PutMapping("/api/acceptRoommateRequest/{id}")
    public ResponseEntity<Roommates> acceptRoommateRequest(@PathVariable Long id) {
        return requestService.acceptRoommateRequest(id);
    }

    @PutMapping("/api/denyRoommateRequest/{id}")
    public ResponseEntity<RoommateRequest> denyRoommateRequest(@PathVariable Long id) {
        return requestService.denyRoommateRequest(id);
    }


}
