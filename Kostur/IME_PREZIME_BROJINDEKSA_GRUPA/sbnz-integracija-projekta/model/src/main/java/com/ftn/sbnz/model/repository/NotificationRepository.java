package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.sbnz.model.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>{

}
