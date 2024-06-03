package com.ftn.sbnz.service.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.dto.RoommateRequestDTO;
import com.ftn.sbnz.model.models.Reservation;
import com.ftn.sbnz.model.models.RoommateRequest;
import com.ftn.sbnz.model.models.Roommates;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.repository.AccommodationRepository;
import com.ftn.sbnz.model.repository.ReservationRepository;
import com.ftn.sbnz.model.repository.RoommateRequestRepository;
import com.ftn.sbnz.model.repository.RoommatesRepository;
import com.ftn.sbnz.model.repository.UserRepository;

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
    public ReservationRepository reservationRepository;


    public ResponseEntity<RoommateRequest> sendRoommateRequest(RoommateRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                                    .orElseThrow(() -> new RuntimeException("User not found"));

        User requestedUser = userRepository.findById(requestDTO.getRequestedUserId())
                                                                .orElseThrow(() -> new RuntimeException("Requested user not found"));

        RoommateRequest request = new RoommateRequest();
        request.setStatus(requestDTO.getStatus());
        request.setUser(user);
        request.setRequestedUser(requestedUser);

        roommateRequestRepository.save(request);
    
        return ResponseEntity.ok(request);
    }

    public ResponseEntity<Roommates> acceptRoommateRequest(Long requestId) {
        RoommateRequest roommateRequest = roommateRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Roommate request not found"));

        roommateRequest.setStatus(RequestStatus.ACCEPTED);

        Roommates roommates=new Roommates();
        roommates.setRoommate1(roommateRequest.getUser());
        roommates.setRoommate2(roommateRequest.getRequestedUser());

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
