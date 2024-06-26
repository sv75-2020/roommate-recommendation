package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.AccommodationReviewDTO;
import com.ftn.sbnz.model.dto.RoommateRequestDTO;
import com.ftn.sbnz.model.dto.UserDTO;
import com.ftn.sbnz.model.exceptions.BadRequestException;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.Notification;
import com.ftn.sbnz.model.models.Payment;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.services.LocationService;
import com.ftn.sbnz.service.services.NotificationService;
import com.ftn.sbnz.service.services.RequestService;
import com.ftn.sbnz.service.services.UserService;
import enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    public LocationService locationService;

    @Autowired
    public NotificationService notificationService;

    @Autowired
    public RequestService requestService;

    @GetMapping(value = "/api/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();

    }

    @GetMapping(value = "/api/recommended")
    public ResponseEntity<List<User>> getRecommendedUsers() {
        return userService.getRecommendedUsers();

    }

    @GetMapping(value = "/api/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        System.out.println("aaaaa");
        return userService.getUser(id);

    }

    @GetMapping(value = "/api/getUserNotifications/{id}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long id) {
        
        return notificationService.getNotifications(id);

    }

    @GetMapping(value = "/api/getAdminNotifications")
    public ResponseEntity<List<Notification>> getUserNotifications() {

        return notificationService.getAdminNotifications();

    }

    @PostMapping(consumes = "application/json", value = "/api/registerUser")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws IOException {
        return userService.registerUser(user);

    }

    @PutMapping(consumes = "application/json", value = "/api/payBill/{id}")
    public ResponseEntity<Map<String,String>> payBill(@PathVariable Long id) throws IOException {
        return userService.payBill(id);

    }

    @PutMapping(consumes = "application/json", value = "/api/payDeposit/{id}")
    public ResponseEntity<Map<String,String>> payDeposit(@PathVariable Long id) throws IOException {
        return userService.payDeposit(id);

    }

     @PutMapping("/api/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(value = "/api/getAllLocations")
    public ResponseEntity<List<Location>> getAllLocations() {
        System.out.println("asaaaa");
        return locationService.getAllLocations();

    }

        @PostMapping(value="/api/uploadPhoto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFiles(@RequestParam("image") MultipartFile image) throws IOException {
        if (image.getSize()>5000000){
            throw new BadRequestException("File is bigger than 5mb!");
        }

        userService.savePhoto(image);

        return new ResponseEntity<>("Uspesno.", HttpStatus.OK);
    }

    @GetMapping(value = "/api/{filename}/file")
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) throws IOException{

        File  file = new File( "C:\\Users\\suput\\Documents\\GitHub\\roommate-recommendation\\Kostur\\IME_PREZIME_BROJINDEKSA_GRUPA\\sbnz-integracija-projekta\\service\\src\\main\\resources\\images\\" + filename);
        byte [] response =  Files.readAllBytes(file.toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(Files.probeContentType(file.toPath())))
                .body(response);
    }

    @GetMapping(value = "/api/findRoommate")
    public ResponseEntity<User> findRoommate() throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        User recommendedUser=userService.findRecommendedUser();
        if (recommendedUser != null) {
            RoommateRequestDTO roommateRequestDTO = new RoommateRequestDTO(RequestStatus.PENDING,user.getId(),recommendedUser.getId());
            requestService.sendRoommateRequest(roommateRequestDTO);
            return ResponseEntity.ok(recommendedUser);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}