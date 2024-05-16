package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    
}
