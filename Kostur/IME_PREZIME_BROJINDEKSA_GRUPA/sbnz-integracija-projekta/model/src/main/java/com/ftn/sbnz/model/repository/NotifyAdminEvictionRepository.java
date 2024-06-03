package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.NotifyAdminEviction;

import org.springframework.stereotype.Repository;

@Repository
public interface NotifyAdminEvictionRepository extends JpaRepository<NotifyAdminEviction, Long> {

}
