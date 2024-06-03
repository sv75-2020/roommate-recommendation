package com.ftn.sbnz.service.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.MonthlyPayment;
import com.ftn.sbnz.model.models.Notification;
import com.ftn.sbnz.model.models.Reservation;
import com.ftn.sbnz.model.models.RoommateRequest;
import com.ftn.sbnz.model.models.UserWarning;
import com.ftn.sbnz.model.repository.MonthlyPaymentRepository;
import com.ftn.sbnz.model.repository.NotificationRepository;
import com.ftn.sbnz.model.repository.ReservationRepository;
import com.ftn.sbnz.model.repository.RoommateRequestRepository;
import com.ftn.sbnz.model.repository.UserRepository;
import com.ftn.sbnz.model.repository.UserWarningRepository;

import enums.RequestStatus;
import enums.ReservationStatus;

@Service
public class NotificationService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public MonthlyPaymentRepository monthlyPaymentRepository;

    @Autowired
    public RoommateRequestRepository roommateRequestRepository;

    @Autowired
    public ReservationRepository reservationRepository;

    @Autowired
    public UserWarningRepository userWarningRepository;

    public ResponseEntity<List<Notification>> getNotifications(Long id) {
        List<Notification> notifications=new ArrayList<>(); 
        LocalDate now = LocalDate.now();
        for(MonthlyPayment mp: monthlyPaymentRepository.findAll()){
            if(mp.getUser().getId()==id && now.getMonth().equals(mp.getPaymentDate().getMonth()) && now.getYear()==mp.getPaymentDate().getYear())
                notifications.add(new Notification("Pay bill for month: "+mp.getPaymentDate().getMonth(), "bill",mp.getPaymentId()));
        }
        for(RoommateRequest request: roommateRequestRepository.findAll()){
            if(request.getRequestedUser().getId()==id && request.getStatus()==RequestStatus.PENDING)
                notifications.add(new Notification("Roommate request from user  "+request.getUser().getFullName(), "roommateRequest", request.getId()));
        }
        for(Reservation reservation: reservationRepository.findAll()){
            if((reservation.getRoommates().getRoommate1().getId()==id || reservation.getRoommates().getRoommate2().getId()==id ) && reservation.getStatus()==ReservationStatus.PENDING)
                notifications.add(new Notification("Reservation request", "reservationRequest", reservation.getId()));
        }
        for(UserWarning warning: userWarningRepository.findAll()){
            if(warning.getUser().getId()==id)
                notifications.add(new Notification("Warning! You havent paid rent", "warning", 0L));
        }
        return ResponseEntity.ok(notifications);
    }
}
