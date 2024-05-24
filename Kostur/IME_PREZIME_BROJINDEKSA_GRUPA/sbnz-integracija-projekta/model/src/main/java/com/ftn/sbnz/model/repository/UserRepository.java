package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.ftn.sbnz.model.models.User;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

}
