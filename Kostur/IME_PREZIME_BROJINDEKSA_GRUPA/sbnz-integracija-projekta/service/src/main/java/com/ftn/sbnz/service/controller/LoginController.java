package com.ftn.sbnz.service.controller;


import com.ftn.sbnz.model.dto.LoginDTO;
import com.ftn.sbnz.model.dto.LoginTokenDTO;
import com.ftn.sbnz.model.exceptions.BadRequestException;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.models.Admin;
import com.ftn.sbnz.service.utils.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @PostMapping(value = "/api/login")
    public ResponseEntity<LoginTokenDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt;
        try {
            User user = (User) authentication.getPrincipal();
            jwt = tokenUtils.generateToken(user.getId(),user.getUsername(), user.getRoles());
        } catch (ClassCastException exception) {
            Admin admin = (Admin) authentication.getPrincipal();
            jwt = tokenUtils.generateToken(admin.getId(),admin.getUsername(), admin.getRoles());
        }
        return ResponseEntity.ok(new LoginTokenDTO(jwt, ""));
    }

    @GetMapping(
            value = "api/logout",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> logoutUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        } else {
            throw new BadRequestException("User is not authenticated!");
        }

    }

}
