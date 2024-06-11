package com.ftn.sbnz.service.services;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.repository.*;
import enums.ReservationStatus;
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
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.dto.AccommodationDTO;
import com.ftn.sbnz.model.dto.UserDTO;

@Service
public class AccommodationService {

    @Autowired
    public AccommodationRepository accommodationRepository;
    @Autowired
    public ReservationService reservationService;
    @Autowired
    public AccommodationPreferencesRepository accommodationPreferencesRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public LocationRepository locationRepository;
    @Autowired
    public RoommatesRepository roommatesRepository;
    @Autowired
    public ReservationRepository reservationRepository;
    @Autowired
    public RoommateRequestRepository roommateRequestRepository;
    @Autowired

    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations() {
        List<AccommodationDTO> accommodationDTOs = accommodationRepository.findAllAccommodationDTOs();
        return ResponseEntity.ok(accommodationDTOs);
    }

    public ResponseEntity<Accommodation> getAccommodationById(Long id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        return accommodation.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<AccommodationDTO>> getHistoryAccommodations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Reservation> notActiveReservations = reservationService.getNotActive(user.getId());
        List<Accommodation> accommodations = notActiveReservations.stream()
                .map(Reservation::getAccommodation)
                .collect(Collectors.toList());

        // Convert list of accommodations to list of AccommodationDTO objects
        List<AccommodationDTO> accommodationDTOs = accommodations.stream()
                .map(AccommodationDTO::new) // Assuming AccommodationDTO constructor is available
                .collect(Collectors.toList());

        // Return the list of AccommodationDTOs within a ResponseEntity
        return ResponseEntity.ok(accommodationDTOs);
    }

      public ResponseEntity<AccommodationPreferences> saveAccommodationPreferences(AccommodationPreferences accommodationPreferences) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user= (User) userRepository.findByUsername(username);

        accommodationPreferences.setUser(user);

        return ResponseEntity.ok(accommodationPreferencesRepository.save(accommodationPreferences));
    }

    public Accommodation findRecommendedAccommodation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user= (User) userRepository.findByUsername(username);

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        //SessionPseudoClock clock = ksession.getSessionClock();
        InputStream template = AccommodationService.class.getResourceAsStream("/rules/cep/accommodation-template.drt");
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"closeToUni", "closeToUni", "10", "1", "1"},
                new String[]{"closeToCenter", "closeToCenter", "30", "2", "1"},
                new String[]{"groundFloor", "groundFloor", "40", "3", "1"},
                new String[]{"topFloor", "topFloor", "150", "4", "1"},
                new String[]{"elevator", "hasElevator", "150", "5", "1"},
                new String[]{"AC", "hasAc", "150", "6", "1"},
        });
        DataProviderCompiler converter = new DataProviderCompiler();
        String drl1 = converter.compile(dataProvider, template);

        //List<Long> recommendedRoommate=new ArrayList<>();
        Long bestMatch = 0L;
        Map<Long, Integer> accommodationMap = new HashMap<>();

        KieSession ksession = createKieSessionFromDRL(drl1);
        ksession.setGlobal("loggedInId", user.getId());
        ksession.setGlobal("bestMatch", bestMatch);
        ksession.setGlobal("accommodationCompatibility", accommodationMap);

        Roommates roommates = null;

        List<RoommateRequest> requests = roommateRequestRepository.findByUserIdOrRequestedUserId(user.getId());
        for(RoommateRequest request : requests) {
            roommates = roommatesRepository.findRoommatesFromRequest(request.getUserId(), request.getRequestedUserId());
            ksession.insert(roommates.getRoommate1());
            ksession.insert(roommates.getRoommate2());
            ksession.insert(roommates);
            AccommodationRequirements accr1 = new AccommodationRequirements(roommates, 0, false, false, false, new ArrayList<Location>());
            ksession.insert(accr1);
            AccommodationPreferences roommate1Preferences = accommodationPreferencesRepository.findByUser(roommates.getRoommate1());
            AccommodationPreferences roommate2Preferences = accommodationPreferencesRepository.findByUser(roommates.getRoommate2());
            ksession.insert(roommate1Preferences);
            ksession.insert(roommate2Preferences);
        }
        locationRepository.findAll().forEach(ksession::insert);
        accommodationRepository.findAll().forEach(ksession::insert);
        reservationRepository.findAll().forEach(ksession::insert);


        ksession.getAgenda().getAgendaGroup("accommodation-forward").setFocus();

        ksession.fireAllRules();

        Long recommendedAccommodation = (Long) ksession.getGlobal("bestMatch");
        System.out.println(recommendedAccommodation);

        Accommodation recommended=accommodationRepository.findById(recommendedAccommodation).orElse(null);

        reservationService.newReservation(roommates,recommended);

        ksession.dispose();

        return recommended;

    }

    private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();
        KieServices kieServices = KieServices.Factory.get();
        kieHelper.addResource(kieServices.getResources().newClassPathResource("rules/cep/accommodationForward.drl"), ResourceType.DRL);
        kieHelper.addResource(kieServices.getResources().newClassPathResource("rules/cep/createAccommodationReq.drl"), ResourceType.DRL);
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }

    public ResponseEntity<Accommodation> getActiveAccommodation(Long id) {
        Accommodation accommodation=null;
        for(Reservation reservation: reservationRepository.findAll()){
            if(reservation.getStatus()== ReservationStatus.ACTIVE && (reservation.getRoommates().getRoommate1().getId()==id || reservation.getRoommates().getRoommate2().getId()==id)){
                accommodation=reservation.getAccommodation();
            }
        }
        return ResponseEntity.ok(accommodation);
    }
}
