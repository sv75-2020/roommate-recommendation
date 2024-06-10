package com.ftn.sbnz.service.services;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import com.ftn.sbnz.model.dto.RoommateRequestDTO;
import com.ftn.sbnz.model.dto.UserDTO;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.repository.*;
import enums.RequestStatus;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.sbnz.model.events.BillPaidEvent;
import com.ftn.sbnz.service.utils.FileUtil;

import java.nio.file.Files;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BillPaidRepository billPaidRepository;

    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public LocationRepository locationRepository;


    public ResponseEntity<Map<String,String>> payBill(Long paymentId){
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user= (User) userRepository.findByUsername(username);

        BillPaidEvent bpe=new BillPaidEvent();
        BillPaid bp=new BillPaid();
        LocalDate currDate = LocalDate.now();
        bp.setPaymentDate(currDate);
        bp.setUser(user);

        bpe.setPaymentDate(currDate);
        bpe.setUser(user);

       
        billPaidRepository.save(bp);

        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isPresent()) {
 
            if(payment.get().getReservation().getRoommates().getRoommate1().getId()==user.getId()){
                payment.get().setPaidRoommate1(true);
            }
            else{
                payment.get().setPaidRoommate2(true);
            }
            paymentRepository.save(payment.get());
        } else {
            throw new EntityNotFoundException("User not found with ID 1");
        }

         Map<String, String> response = new HashMap<>();
        response.put("message", "Success");
        return ResponseEntity.ok(response);
        
    }

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userRepository.findAllUserDTOs();
        return ResponseEntity.ok(userDTOs);
    }

    public ResponseEntity<User> registerUser(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(6,new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role role = new Role("USER");
        role=roleRepository.save(role);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        User u= userRepository.save(user);
        return  ResponseEntity.ok(u);
    }

    public User updateUser(Long id, User userDetails) {
        
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        user.setUsername(userDetails.getUsername());
        user.setFullName(userDetails.getFullName());
        user.setPassword(userDetails.getPassword());
        user.setGender(userDetails.getGender());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setSmoker(userDetails.isSmoker());
        user.setHasPets(userDetails.isHasPets());
        user.setPersonalityType(userDetails.getPersonalityType());
        user.setJobStatus(userDetails.getJobStatus());
        user.setInterests(userDetails.getInterests());
        user.setCleaningHabit(userDetails.getCleaningHabit());
        user.setWorksFromHome(userDetails.isWorksFromHome());
        user.setHasCar(userDetails.isHasCar());
        user.setMoveInMonth(userDetails.getMoveInMonth());
        user.setLikesQuiet(userDetails.isLikesQuiet());
        user.setBudget(userDetails.getBudget());
        user.setLocations(userDetails.getLocations());
        user.setDoesntWantPets(userDetails.isDoesntWantPets());
        user.setDislikesSmokingIndoors(userDetails.isDislikesSmokingIndoors());
        user.setHasRoommate(userDetails.isHasRoommate());
        user.setBlocked(userDetails.isBlocked());

        return userRepository.save(user);
    }

    public ResponseEntity<User> getUser(Long id) {
        return ResponseEntity.ok(userRepository.findById(id).get());
    }
    
    
    private void createDirIfNotExist() {
        //create directory to save the files
        File directory = new File(FileUtil.folderPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public ResponseEntity<String> savePhoto(MultipartFile file) throws IOException {
        try {
            createDirIfNotExist();

            byte[] bytes = new byte[0];
            bytes = file.getBytes();
            System.out.println( " Folder path " + FileUtil.folderPath);
            Files.write(Paths.get(FileUtil.folderPath + file.getOriginalFilename()), bytes);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Files uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body("Exception occurred for: " + file.getOriginalFilename() + "!");
        }

    }

    public User findRecommendedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user= (User) userRepository.findByUsername(username);

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        //SessionPseudoClock clock = ksession.getSessionClock();
        InputStream template = UserService.class.getResourceAsStream("/rules/cep/no-matching-rule-template.drt");
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"doesntWantPets", "true", "hasPets", "true", "70"},
                new String[]{"hasPets", "true", "doesntWantPets", "true", "69"},
                new String[]{"dislikesSmokingIndoors", "true", "smoker", "true", "68"},
                new String[]{"smoker", "true", "dislikesSmokingIndoors", "true", "67"},
                new String[]{"likesQuiet", "true", "likesQuiet", "false", "66"},
                new String[]{"likesQuiet ", "false", "likesQuiet", "true", "65"},
        });


        DataProviderCompiler converter = new DataProviderCompiler();
        String drl1 = converter.compile(dataProvider, template);

        List<Long> recommendedRoommate=new ArrayList<>();

        KieSession ksession = createKieSessionFromDRL(drl1);
        ksession.setGlobal("loggedInId", user.getId());
        ksession.setGlobal("recommendedRoommate", recommendedRoommate);
        ksession.setGlobal("userCompatibility", new HashMap<Long, Integer>());
        ksession.setGlobal("recommendedRoommates", new ArrayList<User>());

        for(User u : userRepository.findAll()){
            ksession.insert(u);
        }
        for(Location location : locationRepository.findAll()){
            ksession.insert(location);
        }

        ksession.getAgenda().getAgendaGroup("roommate-forward").setFocus();

        ksession.fireAllRules();

        List<Long> recommendedUser= (List<Long>) ksession.getGlobal("recommendedRoommate");

        System.out.println(recommendedUser.get(0));

        User recommended=userRepository.findById(recommendedUser.get(0)).get();

        ksession.dispose();

        return recommended;

    }


    private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();
        KieServices kieServices = KieServices.Factory.get();
        kieHelper.addResource(kieServices.getResources().newClassPathResource("rules/cep/roommateForward.drl"), ResourceType.DRL);
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }
}
