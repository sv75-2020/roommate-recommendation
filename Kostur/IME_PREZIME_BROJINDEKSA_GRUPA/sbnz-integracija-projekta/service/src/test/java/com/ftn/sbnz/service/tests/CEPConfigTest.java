package com.ftn.sbnz.service.tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ftn.sbnz.model.events.MonthlyPaymentEvent;
import com.ftn.sbnz.model.events.NotifyAdminForBill;
import com.ftn.sbnz.model.models.Accommodation;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.Payment;
import com.ftn.sbnz.model.models.Reservation;
import com.ftn.sbnz.model.models.Roommates;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.repository.DepositRepository;
import com.ftn.sbnz.model.repository.NotifyAdminEvictionRepository;
import com.ftn.sbnz.model.repository.NotifyAdminForBillRepository;
import com.ftn.sbnz.model.repository.ReservationRepository;
import com.ftn.sbnz.model.repository.UserWarningRepository;
import com.ftn.sbnz.service.services.BillingService;
import com.ftn.sbnz.service.services.ReservationService;
import com.ftn.sbnz.service.services.UserService;

import enums.CleaningHabit;
import enums.Gender;
import enums.JobStatus;
import enums.Month;
import enums.PersonalityType;

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
  public ReservationService reservationService;

  @Autowired 
  public UserService userService;

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
      new ArrayList<>()
  );

  Roommates rm1=new Roommates(1L,user1,user2,true);
  Accommodation a1=new Accommodation(1L,"address1",2,200,true,true,true,true,l1);
    @Test
    public void test() {
      Reservation r1 = reservationService.newReservation(rm1, a1);
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
        ksession.insert(r1);

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
        List<MonthlyPaymentEvent> payments=billingService.addBills();
        
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

        userService.payBill(0L, user1);

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
      Reservation r1 = reservationService.newReservation(rm1, a1);
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
        ksession.insert(r1);
        ksession.fireAllRules();

    }
}

