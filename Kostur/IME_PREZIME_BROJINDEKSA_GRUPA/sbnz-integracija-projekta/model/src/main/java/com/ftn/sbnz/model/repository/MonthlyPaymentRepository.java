package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.events.MonthlyPaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.MonthlyPayment;

@Repository
public interface MonthlyPaymentRepository extends JpaRepository<MonthlyPayment, Long> {


}