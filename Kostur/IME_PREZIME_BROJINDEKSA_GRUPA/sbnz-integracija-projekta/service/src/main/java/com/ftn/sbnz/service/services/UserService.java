package com.ftn.sbnz.service.services;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.ftn.sbnz.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userRepository.findAllUserDTOs();
        return ResponseEntity.ok(userDTOs);
    }

    public ResponseEntity<User> registerUser(User user) {
        User u= userRepository.save(user);
        return  ResponseEntity.ok(u);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        user.setUsername(userDetails.getUsername());
        user.setFullName(userDetails.getFullName());
        user.setPassword(userDetails.getPassword());
        user.setGender(userDetails.getGender());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setSmoker(userDetails.isSmoker());
        user.setHasPets(userDetails.isHasPets());
        user.setPersonalityType(userDetails.getPersonalityType());
        user.setJobStatus(userDetails.getJobStatus());
        user.setInterests(userDetails.getInterests());
        user.setCleaningHabit(userDetails.getCleaningHabit());
        user.setWorksFromHome(userDetails.isWorksFromHome());
        user.setHasCar(userDetails.isHasCar());
        user.setMoveInMonth(userDetails.getMoveInMonth());
        user.setLikesQuiet(userDetails.isLikesQuiet());
        user.setBudget(userDetails.getBudget());
        user.setLocations(userDetails.getLocations());
        user.setDoesntWantPets(userDetails.isDoesntWantPets());
        user.setDislikesSmokingIndoors(userDetails.isDislikesSmokingIndoors());
        user.setHasRoommate(userDetails.isHasRoommate());
        user.setBlocked(userDetails.isBlocked());

        return userRepository.save(user);
    }
    

    
}
