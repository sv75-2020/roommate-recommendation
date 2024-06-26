package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.UserWarning;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWarningRepository extends JpaRepository<UserWarning, Long> {

}
