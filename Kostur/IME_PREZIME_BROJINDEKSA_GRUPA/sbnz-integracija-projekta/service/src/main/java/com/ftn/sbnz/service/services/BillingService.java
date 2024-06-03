package com.ftn.sbnz.service.services;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.MonthlyPaymentEvent;
import com.ftn.sbnz.model.models.MonthlyPayment;
import com.ftn.sbnz.model.models.Payment;
import com.ftn.sbnz.model.models.Reservation;
import com.ftn.sbnz.model.repository.MonthlyPaymentRepository;
import com.ftn.sbnz.model.repository.PaymentRepository;
import com.ftn.sbnz.model.repository.ReservationRepository;


@Service
public class BillingService {
    @Autowired
    public PaymentRepository paymentRepository;
    
    @Autowired 
    public ReservationRepository reservationRepository;

    @Autowired
    public MonthlyPaymentRepository monthlyPaymentRepository;

    public List<MonthlyPayment> addBills(){
        List<MonthlyPayment> payments=new ArrayList<MonthlyPayment>();
        List<Reservation> reservations = reservationRepository.findAll();
        for(Reservation r : reservations){
            Payment p=new Payment();
            p.setPaidRoommate1(false);
            p.setPaidRoommate2(false);
            LocalDate dateAfterSevenDays = LocalDate.now().plusDays(7);
            p.setPaymentDue(dateAfterSevenDays);
            p.setReservation(r);
            paymentRepository.save(p);

            MonthlyPaymentEvent mpe1=new MonthlyPaymentEvent();
            mpe1.setPaymentDate(LocalDate.now());
            mpe1.setUser(r.getRoommates().getRoommate1());

            MonthlyPayment mp1=new MonthlyPayment();
            mp1.setPaymentDate(LocalDate.now());
            mp1.setUser(r.getRoommates().getRoommate1());
            mp1.setPaymentId(p.getId());
            monthlyPaymentRepository.save(mp1);
            payments.add(mp1);

            MonthlyPaymentEvent mpe2=new MonthlyPaymentEvent();
            mpe2.setPaymentDate(LocalDate.now());
            mpe2.setUser(r.getRoommates().getRoommate2());

            MonthlyPayment mp2=new MonthlyPayment();
            mp2.setPaymentDate(LocalDate.now());
            mp2.setUser(r.getRoommates().getRoommate1());
            mp2.setPaymentId(p.getId());
            monthlyPaymentRepository.save(mp2);
            payments.add(mp2);

        }
        return payments;
    }

    public void checkPayments(){
        for(Reservation r : reservationRepository.findAll()){

        }
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleMonthlyBilling() {
        addBills();
    }

}
