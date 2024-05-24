package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Roommates;

import org.springframework.stereotype.Repository;

@Repository
public interface RoommatesRepository extends JpaRepository<Roommates, Long> {
    
    
}
