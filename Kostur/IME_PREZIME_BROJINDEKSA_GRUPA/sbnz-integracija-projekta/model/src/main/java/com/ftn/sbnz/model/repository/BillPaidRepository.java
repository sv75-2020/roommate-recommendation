package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.events.BillPaidEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface BillPaidRepository extends JpaRepository<BillPaidEvent, Long>  {

}
