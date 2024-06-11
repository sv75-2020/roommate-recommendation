package com.ftn.sbnz.service.tests;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.ftn.sbnz.model.dto.PopularLocationsDTO;
import com.ftn.sbnz.model.models.*;
import enums.*;
import com.ftn.sbnz.model.repository.*;
import org.drools.core.time.SessionPseudoClock;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ftn.sbnz.service.services.BillingService;
import com.ftn.sbnz.service.services.ReservationService;
import com.ftn.sbnz.service.services.UserService;
import enums.CleaningHabit;
import enums.Gender;
import enums.JobStatus;
import enums.Month;
import enums.PersonalityType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class CEPConfigTest {

  @Autowired
  public NotifyAdminEvictionRepository notifyAdminEvictionRepository;

  @Autowired
  public NotifyAdminForBillRepository notifyAdminForBillRepository;

  @Autowired
  public UserWarningRepository userWarningRepository;

  @Autowired 
  public BillingService billingService;

  @Autowired
  public UserRepository userRepository;

  @Autowired 
  public ReservationService reservationService;

  @Autowired 
  public UserService userService;

  @Autowired
          public LocationRepository locationRepository;

  Location l1=new Location(1L,"Grbavica");
  Location l2=new Location(2L,"Liman");
  Location l3=new Location(3L,"Banatic");
  Location l4=new Location(4L,"Telep");
  User user1 = new User(
          1L,
          "user1@example.com",
          "John Doe",
          "password123",
          Gender.MALE,
          LocalDate.of(2001, 10, 10), // Note: Date constructor is deprecated, consider using java.time.LocalDate
          true,
          false,
          PersonalityType.EXTROVERT,
          JobStatus.EMPLOYED,
          "Reading, Traveling, Cooking",
          CleaningHabit.OFTEN,
          true,
          true,
          Month.SEPTEMBER,
          true,
          2000,
          List.of(l1, l2),
          false,
          false,
          false,
          false,
          "",
          new ArrayList<>()
      );
User user2 = new User(
       2L,
          "user2@example.com",
          "Jane Smith",
          "password456",
          Gender.FEMALE,
          LocalDate.of(2002, 10, 10), // Note: Date constructor is deprecated, consider using java.time.LocalDate
          true,
          false,
          PersonalityType.INTROVERT,
          JobStatus.UNEMPLOYED,
          "Hiking, Painting, Music",
          CleaningHabit.ONCE_IN_A_WHILE,
          false,
          false,
          Month.APRIL,
          false,
          1500,
          List.of(l1, l3),
          true,
          false,
          false,
          false,
          "",
          new ArrayList<>()
      );        
      
      User user3 = new User(
        3L,
        "user3@example.com",
        "John Does",
        "password123",
        Gender.MALE,
        LocalDate.of(2002, 10, 10), // Note: Date constructor is deprecated, consider using java.time.LocalDate
        true,
        false,
        PersonalityType.INTROVERT,
        JobStatus.EMPLOYED,
        "Reading, Traveling, Cooking",
        CleaningHabit.OFTEN,
        true,
        true,
        Month.SEPTEMBER,
        true,
        2000,
        List.of(l1, l4),
        false,
        false,
        false,
        false,
        "",
        new ArrayList<>()
    );
    User user4 = new User(
      4L,
      "user3@example.com",
      "John Doess",
      "password123",
      Gender.MALE,
      LocalDate.of(2010, 10, 10), // Note: Date constructor is deprecated, consider using java.time.LocalDate
      false,
      false,
      PersonalityType.INTROVERT,
      JobStatus.EMPLOYED,
      "Reading, Traveling, Cooking",
      CleaningHabit.OFTEN,
      true,
      true,
      Month.SEPTEMBER,
      true,
      2000,
      List.of(l1, l4),
      false,
      false,
      false,
      false,
      "",
      new ArrayList<>()
  );

  Roommates rm1=new Roommates(1L,user1,user2);
  AccommodationRequirements accr1 = new AccommodationRequirements(rm1, 0, false, false, false, new ArrayList<Location>());
  Accommodation a1=new Accommodation(1L,"address1",2,200,true,true,true,true,true,true,true,true,true,true,l1);
    @Test
    public void test() {
      //Reservation r1 = reservationService.newReservation(rm1, a1);
      KieServices ks = KieServices.Factory.get();
      KieContainer kContainer = ks.getKieClasspathContainer(); 
      //KieSession ksession = kContainer.newKieSession("cepKsession");
      //SessionPseudoClock clock = ksession.getSessionClock();
      InputStream template = CEPConfigTest.class.getResourceAsStream("/rules/cep/accommodation-template.drt");
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
      Map<Long, Integer> accommodationMap = new HashMap<>();
      accommodationMap.put(a1.getId(), 0);

      InputStream template2 = CEPConfigTest.class.getResourceAsStream("/rules/cep/no-matching-rule-template.drt");
      DataProvider dataProvider2 = new ArrayDataProvider(new String[][]{
              new String[]{"doesntWantPets", "true", "hasPets", "true", "80"},
              new String[]{"hasPets", "true", "doesntWantPets", "true", "79"},
              new String[]{"dislikesSmokingIndoors", "true", "smoker", "true", "78"},
              new String[]{"smoker", "true", "dislikesSmokingIndoors", "true", "77"},
              new String[]{"likesQuiet", "true", "likesQuiet", "false", "76"},
              new String[]{"likesQuiet ", "false", "likesQuiet", "true", "75"},
      });

      DataProviderCompiler converter2 = new DataProviderCompiler();
      String drl2 = converter2.compile(dataProvider2, template2);

      KieSession ksession = createKieSessionFromDRL(drl1, drl2);
      ksession.setGlobal("loggedInId", user1.getId());
      //ksession.setGlobal("userCompatibility", new HashMap<Long, Integer>());
      ksession.setGlobal("accommodationCompatibility", accommodationMap);
      ksession.setGlobal("notifyAdminForBillRepository", notifyAdminForBillRepository);
      ksession.setGlobal("userWarningRepository", userWarningRepository);
      ksession.setGlobal("notifyAdminEvictionRepository", notifyAdminEvictionRepository);
   
      ksession.insert(user1);
      ksession.insert(user2);
      ksession.insert(user3);
      ksession.insert(user4);
      ksession.insert(rm1);
      ksession.insert(a1);
      //ksession.insert(r1);
      //ksession.getAgenda().getAgendaGroup("roommate-forward").setFocus();

      ksession.fireAllRules();
    }

    @Test
    public void test2() {
      Reservation r1 = reservationService.newReservation(rm1, a1);
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession ksession = kContainer.newKieSession("cepKsession");
        SessionPseudoClock clock = ksession.getSessionClock();
        System.out.println(billingService);
        List<MonthlyPayment> payments=billingService.addBills();
        
        ksession.setGlobal("loggedInId", user1.getId());
        ksession.setGlobal("compatibilityLevel", 0);
        ksession.setGlobal("recommendedRoommates", new ArrayList<User>());
        ksession.setGlobal("notifyAdminForBillRepository", notifyAdminForBillRepository);
        ksession.setGlobal("userWarningRepository", userWarningRepository);
        ksession.setGlobal("notifyAdminEvictionRepository", notifyAdminEvictionRepository);

         
        ksession.insert(user1);
        ksession.insert(user2);
        ksession.insert(user3);
        ksession.insert(user4);
        ksession.insert(payments.get(0));
        ksession.insert(payments.get(1));
        ksession.insert(payments.get(2));
        ksession.insert(rm1);
        ksession.insert(a1);
        ksession.insert(r1);
        ksession.fireAllRules();

        userService.payBill(0L);

        clock.advanceTime(10, TimeUnit.DAYS);
       
        ksession.fireAllRules();
        
        for(Payment p : billingService.paymentRepository.findAll()){
          System.out.println("roommate1 "+p.isPaidRoommate1());
          System.out.println("roommate2 "+p.isPaidRoommate2());
        }
        for(NotifyAdminForBill n: notifyAdminForBillRepository.findAll()){
          System.out.println(n.getUser());
        }
     
    }
    @Test
    public void test_deposit_cep() {
      //Reservation r1 = reservationService.newReservation(rm1, a1);
      KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession ksession = kContainer.newKieSession("cepKsession");
        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.setGlobal("loggedInId", user1.getId());
        ksession.setGlobal("compatibilityLevel", 0);
        ksession.setGlobal("recommendedRoommates", new ArrayList<User>());
        ksession.setGlobal("notifyAdminForBillRepository", notifyAdminForBillRepository);
        ksession.setGlobal("userWarningRepository", userWarningRepository);
        ksession.setGlobal("notifyAdminEvictionRepository", notifyAdminEvictionRepository);

        ksession.insert(user1);
        ksession.insert(user2);
        ksession.insert(user3);
        ksession.insert(user4);
        ksession.insert(rm1);
        ksession.insert(a1);
        //ksession.insert(r1);
        ksession.insert(accr1);
        ksession.getAgenda().getAgendaGroup("accommodation-forward").setFocus();
        ksession.fireAllRules();

    }

    @Test
    public void findRecommendedUser() {
    User user= (User) userRepository.findById(13L).get();

    KieServices ks = KieServices.Factory.get();
    KieContainer kContainer = ks.getKieClasspathContainer();
    //SessionPseudoClock clock = ksession.getSessionClock();
    InputStream template = UserService.class.getResourceAsStream("/rules/cep/no-matching-rule-template.drt");
    DataProvider dataProvider = new ArrayDataProvider(new String[][]{
            new String[]{"doesntWantPets", "true", "hasPets", "true", "80"},
            new String[]{"hasPets", "true", "doesntWantPets", "true", "79"},
            new String[]{"dislikesSmokingIndoors", "true", "smoker", "true", "78"},
            new String[]{"smoker", "true", "dislikesSmokingIndoors", "true", "77"},
            new String[]{"likesQuiet", "true", "likesQuiet", "false", "76"},
            new String[]{"likesQuiet ", "false", "likesQuiet", "true", "75"},
    });


    DataProviderCompiler converter = new DataProviderCompiler();
    String drl1 = converter.compile(dataProvider, template);


    KieSession ksession = createKieSessionFromDRL1(drl1);
    ksession.setGlobal("loggedInId", user.getId());
    ksession.setGlobal("recommendedRoommate", 0L);
    ksession.setGlobal("userCompatibility", new HashMap<Long, Integer>());
    ksession.setGlobal("recommendedRoommates", new ArrayList<User>());

    for(User u : userRepository.findAll()){
      ksession.insert(u);
    }
    for(Location location : locationRepository.findAll()){
      ksession.insert(location);
    }

    //ksession.getAgenda().getAgendaGroup("roommate-forward").setFocus();

    ksession.fireAllRules();

    Long recommendedUser=(Long) ksession.getGlobal("recommendedRoommate");

    System.out.println(recommendedUser);
    User recommended=userRepository.findById(recommendedUser).get();

    ksession.dispose();

  }

  private KieSession createKieSessionFromDRL1(String drl){
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


    public void top3Locations() {
      KieServices ks = KieServices.Factory.get();
      KieContainer kContainer = ks.getKieClasspathContainer();
      KieSession kSession = kContainer.newKieSession("cepKsession");
      kSession.insert(l1);
      kSession.insert(l2);
      kSession.insert(l3);
      kSession.insert(l4);
      Reservation reservation = new Reservation();
      reservation.setCreated(LocalDate.now());
      reservation.setAccommodation(a1);
      reservation.setStatus(ReservationStatus.ACTIVE);
      kSession.insert(reservation);
      reservation = new Reservation();
      reservation.setCreated(LocalDate.now());
      reservation.setAccommodation(a1);
      kSession.insert(reservation);
      reservation = new Reservation();
      reservation.setCreated(LocalDate.now());
      reservation.setAccommodation(a1);
      kSession.insert(reservation);
      AccommodationReview review1 = new AccommodationReview();
      review1.setAccommodation(a1);
      review1.setRating(5.0);
      kSession.insert(review1);
      review1 = new AccommodationReview();
      review1.setAccommodation(a1);
      review1.setRating(5.0);
      kSession.insert(review1);
      review1 = new AccommodationReview();
      review1.setAccommodation(a1);
      review1.setRating(2.0);
      kSession.insert(review1);

      List<PopularLocationsDTO> popularLocations = new ArrayList<>();

      QueryResults results = kSession.getQueryResults("getMostPopularLocationsForMonth", 6, 2024);

      for (QueryResultsRow row : results) {
        String locationName = (String) row.get("$locationName");
        Long count = (Long) row.get("$count");

        popularLocations.add(new PopularLocationsDTO(locationName, count));
      }

      List<PopularLocationsDTO> top3PopularLocations = popularLocations.stream()
              .sorted(Comparator.comparingLong(PopularLocationsDTO::getCount).reversed()) // Sort by count in descending order
              .limit(3) // Limit to top 3
              .collect(Collectors.toList()); // Collect the result back into a list
      System.out.println(top3PopularLocations.size());

      QueryResults results2 = kSession.getQueryResults("findAverageByLocation");

      for (QueryResultsRow row : results2) {
        String locationName = (String) row.get("$locationName");
        Double count = (Double) row.get("$averageRating");
        System.out.println(locationName + count.toString());
      }

      QueryResults results3 = kSession.getQueryResults("findAverageUserRating", 2L);

      for (QueryResultsRow row : results3) {
        Double count = (Double) row.get("$averageRating");
        System.out.println(count.toString());
      }

      QueryResults results4 = kSession.getQueryResults("countNewReservations", 2024, 6);

      // Iterate over the results
      for (QueryResultsRow row : results4) {
        Long totalEarnings = (Long) row.get("$reservationCount");
        System.out.println("Total earnings for " + 2024 + "/" + 6 + " is " + totalEarnings);
      }


    }

  private KieSession createKieSessionFromDRL(String drl, String drl2){
    KieHelper kieHelper = new KieHelper();
    kieHelper.addContent(drl, ResourceType.DRL);
    kieHelper.addContent(drl2, ResourceType.DRL);

    Results results = kieHelper.verify();
    KieServices kieServices = KieServices.Factory.get();
    kieHelper.addResource(kieServices.getResources().newClassPathResource("rules/cep/accommodationForward.drl"), ResourceType.DRL);
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

