package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    
}
