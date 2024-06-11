package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("select r from Role r where r.name=:userRole")
    Role findByName(String userRole);
}
