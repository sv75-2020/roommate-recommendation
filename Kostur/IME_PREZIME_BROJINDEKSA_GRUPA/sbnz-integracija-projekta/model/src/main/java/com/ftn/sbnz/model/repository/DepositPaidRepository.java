package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.events.DepositPaidEvent;
import com.ftn.sbnz.model.models.DepositNotPaid;
import com.ftn.sbnz.model.models.DepositPaid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositPaidRepository extends JpaRepository<DepositPaidEvent, Long> {
}
