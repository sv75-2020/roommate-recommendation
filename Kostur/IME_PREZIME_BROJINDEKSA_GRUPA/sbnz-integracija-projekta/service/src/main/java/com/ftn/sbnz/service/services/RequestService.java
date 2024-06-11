package com.ftn.sbnz.service.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.dto.RoommateRequestDTO;

import enums.RequestStatus;
import enums.ReservationStatus;

@Service
public class RequestService {

    @Autowired 
    public UserRepository userRepository;

    @Autowired
    public RoommateRequestRepository roommateRequestRepository;

    @Autowired
    public AccommodationRepository accommodationRepository;

    @Autowired
    public RoommatesRepository roommatesRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public ReservationRepository reservationRepository;


    public ResponseEntity<RoommateRequest> sendRoommateRequest(RoommateRequestDTO requestDTO) {
        RoommateRequest request = new RoommateRequest();
        request.setStatus(requestDTO.getStatus());
        request.setUserId(requestDTO.getUserId());
        request.setRequestedUserId(requestDTO.getRequestedUserId());
        RoommateRequest saved = roommateRequestRepository.save(request);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<Roommates> acceptRoommateRequest(Long requestId) {
        RoommateRequest roommateRequest = roommateRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Roommate request not found"));

        roommateRequest.setStatus(RequestStatus.ACCEPTED);

        Roommates roommates=new Roommates();
        User user = userRepository.findById(roommateRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User requestedUser = userRepository.findById(roommateRequest.getRequestedUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setHasRoommate(true);
        user = userRepository.save(user);
        requestedUser.setHasRoommate(true);
        requestedUser=userRepository.save(requestedUser);

        roommates.setRoommate1(user);
        roommates.setRoommate2(requestedUser);

        roommateRequestRepository.save(roommateRequest);

        return ResponseEntity.ok(roommatesRepository.save(roommates));
    }

    public ResponseEntity<RoommateRequest> denyRoommateRequest(Long requestId) {
        RoommateRequest roommateRequest = roommateRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Roommate request not found"));

        roommateRequest.setStatus(RequestStatus.DENIED);

        return ResponseEntity.ok(roommateRequestRepository.save(roommateRequest));
    }

    public ResponseEntity<Map<String,String>> acceptReservationRequest(Long id) {
        Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reservation request not found"));

        reservation.setStatus(ReservationStatus.ACTIVE);

        reservationRepository.save(reservation);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Success");

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String,String>> denyReservationRequest(Long id) {
        Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reservation request not found"));

        reservation.setStatus(ReservationStatus.DENIED);

        reservationRepository.save(reservation);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Success");

        return ResponseEntity.ok(response);
    }


}
