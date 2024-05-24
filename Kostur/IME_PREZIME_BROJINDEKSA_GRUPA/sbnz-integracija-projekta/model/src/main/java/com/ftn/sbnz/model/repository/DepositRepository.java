package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.models.DepositPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<DepositPayment, Long> {

}
