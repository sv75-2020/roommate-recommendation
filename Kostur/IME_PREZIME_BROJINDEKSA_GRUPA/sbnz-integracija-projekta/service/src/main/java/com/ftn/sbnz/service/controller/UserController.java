package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.UserDTO;
import com.ftn.sbnz.model.models.Payment;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/api/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();

    }

    @PostMapping(consumes = "application/json", value = "/api/registerUser")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws IOException {
        return userService.registerUser(user);

    }

    @PutMapping(consumes = "application/json", value = "/api/payBill/{id}")
    public ResponseEntity<String> payBill(@PathVariable Long id) throws IOException {
        return userService.payBill(id);

    }

     @PutMapping("/api/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

}