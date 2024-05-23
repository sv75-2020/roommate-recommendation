package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.events.BillPaidEvent;

public interface BillPaidRepository extends JpaRepository<BillPaidEvent, Long>  {

}
