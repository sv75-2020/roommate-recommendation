package com.ftn.sbnz.service.services;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.BillPaidEvent;
import com.ftn.sbnz.model.models.Payment;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.repository.BillPaidRepository;
import com.ftn.sbnz.model.repository.PaymentRepository;
import com.ftn.sbnz.model.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BillPaidRepository billPaidRepository;

    @Autowired
    public PaymentRepository paymentRepository;

    public void payBill(Long paymentId,User user){
        BillPaidEvent bp=new BillPaidEvent();
        LocalDate currDate = LocalDate.now();
        bp.setPaymentDate(currDate);
        Optional<User> optionalUser = userRepository.findById(1L);
        if (optionalUser.isPresent()) {
            bp.setUser(optionalUser.get());
        } else {
            throw new EntityNotFoundException("User not found with ID 1");
        }
        billPaidRepository.save(bp);

        Optional<Payment> payment = paymentRepository.findById(1L);
        if (payment.isPresent()) {
            if(payment.get().getReservation().getRoommates().getRoommate1().getId()==user.getId()){
                payment.get().setPaidRoommate1(true);
            }
            else{
                payment.get().setPaidRoommate2(true);
            }
            paymentRepository.save(payment.get());
        } else {
            throw new EntityNotFoundException("User not found with ID 1");
        }
        
    }
}
