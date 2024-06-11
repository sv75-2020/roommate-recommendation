package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.models.DepositNotPaid;
import com.ftn.sbnz.model.models.DepositPaid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositPaidRepository extends JpaRepository<DepositPaid, Long> {
}
