package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.ftn.sbnz.model.models.User;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    @Query("SELECT new com.ftn.sbnz.model.dto.UserDTO(u) FROM User u")
    List<UserDTO> findAllUserDTOs();


}
