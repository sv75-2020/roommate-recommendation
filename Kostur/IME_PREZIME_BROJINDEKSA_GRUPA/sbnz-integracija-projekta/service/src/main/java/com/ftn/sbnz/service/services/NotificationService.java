package com.ftn.sbnz.service.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ftn.sbnz.model.events.DepositNotPaidEvent;
import com.ftn.sbnz.model.events.NotifyAdminEvictionEvent;
import com.ftn.sbnz.model.events.NotifyAdminForBillEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    @Autowired
    public NotifyAdminForBillRepository notifyAdminForBillRepository;
    @Autowired
    public NotifyAdminEvictionRepository notifyAdminEvictionRepository;
    @Autowired
    private DepositNotPaidRepository depositNotPaidRepository;
    @Autowired
    private DepositRepository depositRepository;

    public ResponseEntity<List<Notification>> getNotifications(Long id) {
        List<Notification> notifications=new ArrayList<>(); 
        LocalDate now = LocalDate.now();
        for(MonthlyPayment mp: monthlyPaymentRepository.findAll()){
            if(mp.getUser().getId()==id && now.getMonth().equals(mp.getPaymentDate().getMonth()) && now.getYear()==mp.getPaymentDate().getYear())
                notifications.add(new Notification("Pay bill for month: "+mp.getPaymentDate().getMonth(), "bill",mp.getPaymentId()));
        }
        for(RoommateRequest request: roommateRequestRepository.findAll()){
            if(request.getRequestedUserId()==id && request.getStatus()==RequestStatus.PENDING)
                notifications.add(new Notification("Roommate request from user  "+request.getUserId(), "roommateRequest", request.getId()));
        }
        for(Reservation reservation: reservationRepository.findAll()){
            if((reservation.getRoommates().getRoommate1().getId()==id || reservation.getRoommates().getRoommate2().getId()==id ) && reservation.getStatus()==ReservationStatus.PENDING)
                notifications.add(new Notification("Reservation request", "reservationRequest", reservation.getId()));
        }
        for(UserWarning warning: userWarningRepository.findAll()){
            if(warning.getUser().getId()==id)
                notifications.add(new Notification("Warning! You haven't paid rent", "warning", 0L));
        }
        for(DepositPayment depositPayment: depositRepository.findAll()){
            if(depositPayment.getUser().getId()==id && !depositPayment.isPaid())
                notifications.add(new Notification("You have one day to pay deposit.", "deposit", depositPayment.getId()));
        }

        return ResponseEntity.ok(notifications);
    }

    public ResponseEntity<List<Notification>> getAdminNotifications(){
        List<Notification> notifications=new ArrayList<>();
        for(NotifyAdminForBillEvent notify: notifyAdminForBillRepository.findAll()){
                notifications.add(new Notification("User "+notify.getUser().getFullName()+" didn't pay the bill!", "billNotPaid",0L));
        }
        for(NotifyAdminEvictionEvent notify: notifyAdminEvictionRepository.findAll()){
            notifications.add(new Notification("User "+notify.getUser().getFullName()+" blocked because they didn't pay the bill!", "eviction",0L));
        }
        for(DepositNotPaidEvent depositNotPaidEvent: depositNotPaidRepository.findAll()){
            notifications.add(new Notification("User" + depositNotPaidEvent.getUser().getFullName()+"didn't pay deposit.", "warning", 0L));
        }
        return ResponseEntity.ok(notifications);
    }
}
