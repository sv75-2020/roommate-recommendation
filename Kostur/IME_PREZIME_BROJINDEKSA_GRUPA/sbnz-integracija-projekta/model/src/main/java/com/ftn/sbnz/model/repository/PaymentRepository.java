package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    
}
