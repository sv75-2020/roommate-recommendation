package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.events.NotifyAdminForBillEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.NotifyAdminForBill;

import org.springframework.stereotype.Repository;

@Repository
public interface NotifyAdminForBillRepository extends JpaRepository<NotifyAdminForBillEvent, Long> {

}
