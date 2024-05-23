package com.ftn.sbnz.model.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.Accommodation;
import com.ftn.sbnz.model.models.DepositPayment;
import com.ftn.sbnz.model.models.Reservation;
import com.ftn.sbnz.model.models.Roommates;
import com.ftn.sbnz.model.repository.DepositRepository;
import com.ftn.sbnz.model.repository.ReservationRepository;

import enums.ReservationStatus;

@Service
public class ReservationService {
    
  @Autowired
  public ReservationRepository reservationRepository;

  @Autowired
  public DepositRepository depositRepository;

  public Reservation newReservation(Roommates rm1, Accommodation a1) {
      Reservation reservation = new Reservation(1L,LocalDate.now(), false, a1,rm1,new ArrayList<>(), new ArrayList<>(), ReservationStatus.PENDING);
      List<DepositPayment> depositPayments = new ArrayList<>();
      DepositPayment depositPayment1 = new DepositPayment(1L, rm1.getRoommate1(), false);
      DepositPayment depositPayment2 = new DepositPayment(2L, rm1.getRoommate2(), false);
      depositPayments.add(depositPayment1);
      depositPayments.add(depositPayment2);
      reservation.setDepositPayments(depositPayments);
      depositRepository.save(depositPayment1);
      depositRepository.save(depositPayment2);
      reservationRepository.save(reservation);
      return reservation;
  }
  public ReservationService() {
    System.out.println("AA");
  }
}
