package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.events.NotifyAdminForBill;

public interface NotifyAdminForBillRepository extends JpaRepository<NotifyAdminForBill, Long> {

}
