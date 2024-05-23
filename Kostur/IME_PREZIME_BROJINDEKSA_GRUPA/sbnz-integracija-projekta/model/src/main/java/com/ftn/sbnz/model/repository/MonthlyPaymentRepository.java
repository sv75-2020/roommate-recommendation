package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.events.MonthlyPaymentEvent;

public interface MonthlyPaymentRepository extends JpaRepository<MonthlyPaymentEvent, Long> {
    
    
}