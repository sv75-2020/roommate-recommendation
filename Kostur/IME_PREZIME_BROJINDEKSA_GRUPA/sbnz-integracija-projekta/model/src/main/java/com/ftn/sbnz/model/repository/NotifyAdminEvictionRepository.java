package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.events.NotifyAdminEvictionEvent;

import org.springframework.stereotype.Repository;

@Repository
public interface NotifyAdminEvictionRepository extends JpaRepository<NotifyAdminEvictionEvent, Long> {

}
