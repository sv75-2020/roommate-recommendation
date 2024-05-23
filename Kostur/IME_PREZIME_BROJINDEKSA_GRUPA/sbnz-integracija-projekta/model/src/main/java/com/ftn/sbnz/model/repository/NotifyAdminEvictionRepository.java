package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.events.NotifyAdminEviction;

public interface NotifyAdminEvictionRepository extends JpaRepository<NotifyAdminEviction, Long> {

}
