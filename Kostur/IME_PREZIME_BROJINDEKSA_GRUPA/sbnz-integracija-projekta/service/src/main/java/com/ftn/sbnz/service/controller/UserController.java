package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.UserDTO;
import com.ftn.sbnz.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/api/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();

    }

}