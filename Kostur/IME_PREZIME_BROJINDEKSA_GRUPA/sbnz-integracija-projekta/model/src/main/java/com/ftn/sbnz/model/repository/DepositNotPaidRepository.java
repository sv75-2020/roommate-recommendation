package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.events.DepositNotPaidEvent;
import com.ftn.sbnz.model.models.DepositNotPaid;
import com.ftn.sbnz.model.models.MonthlyPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositNotPaidRepository extends JpaRepository<DepositNotPaidEvent, Long> {
}
