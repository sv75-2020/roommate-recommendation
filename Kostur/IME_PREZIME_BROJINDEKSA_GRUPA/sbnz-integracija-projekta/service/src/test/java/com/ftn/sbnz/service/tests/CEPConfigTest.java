package com.ftn.sbnz.service.tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.User;

import enums.CleaningHabit;
import enums.Gender;
import enums.JobStatus;
import enums.Month;
import enums.PersonalityType;



public class CEPConfigTest {

    @Test
    public void test() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession ksession = kContainer.newKieSession("cepKsession");
        SessionPseudoClock clock = ksession.getSessionClock();
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
          List.of("Reading", "Traveling", "Cooking"),
          CleaningHabit.OFTEN,
          true,
          true,
          Month.SEPTEMBER,
          true,
          2000,
          List.of(l1, l2),
          false,
          false,
          false
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
          List.of("Hiking", "Painting", "Music"),
          CleaningHabit.ONCE_IN_A_WHILE,
          false,
          false,
          Month.APRIL,
          false,
          1500,
          List.of(l1, l3),
          true,
          false,
          false
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
        List.of("Reading", "Traveling", "Cooking"),
        CleaningHabit.OFTEN,
        true,
        true,
        Month.SEPTEMBER,
        true,
        2000,
        List.of(l1, l4),
        false,
        false,
        false
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
      List.of("Reading", "Traveling", "Cooking"),
      CleaningHabit.OFTEN,
      true,
      true,
      Month.SEPTEMBER,
      true,
      2000,
      List.of(l1, l4),
      false,
      false,
      false
  );
      ksession.setGlobal("loggedInId", user1.getId());
      ksession.setGlobal("compatibilityLevel", 0);
      ksession.setGlobal("recommendedRoommates", new ArrayList<User>());
   
        ksession.insert(user1);
        ksession.insert(user2);
        ksession.insert(user3);
        ksession.insert(user4);

        ksession.fireAllRules();

       
        //clock.advanceTime(2, TimeUnit.HOURS);
      /**
       * Ukoliko je isti kupac kupio bar 3 
       * nova Samsung telefona u posljednjih 5 
       * minuta čija je cena veća od 800e, 
       * kreiraj kupon za 10% popusta
       * na sledeću kupovinu tableta. Ukoliko kupi tablet, na cenu
            se primenjuje kupon.
       */
    }
    /*@Test
    public void test2() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession ksession = kContainer.newKieSession("cepKsession");
        SessionPseudoClock clock = ksession.getSessionClock();
        TransactionEvent t1 = new TransactionEvent(1L, 850.00, "Samsung");
        TransactionEvent t2 = new TransactionEvent(1L, 880.00, "Samsung");
        TransactionEvent t3 = new TransactionEvent(1L, 890.00, "Samsung");
        TransactionEvent t4 = new TransactionEvent(1L, 890.00, "Samsung");
        t1.setExecutionTime(new Date(clock.getCurrentTime()));
        t2.setExecutionTime(new Date(clock.getCurrentTime()));
        t3.setExecutionTime(new Date(clock.getCurrentTime()));
        ksession.insert(t1);
        ksession.insert(t2);
        ksession.insert(t3);
        ksession.fireAllRules();

        clock.advanceTime(10, TimeUnit.MINUTES);
        t4.setExecutionTime(new Date(clock.getCurrentTime()));
        t4.setPhone("asdas");
        ksession.insert(t4);
        ksession.fireAllRules();
        //clock.advanceTime(2, TimeUnit.HOURS);
      *
       * Ukoliko je isti kupac kupio bar 3 
       * nova Samsung telefona u posljednjih 5 
       * minuta čija je cena veća od 800e, 
       * kreiraj kupon za 10% popusta
       * na sledeću kupovinu tableta. Ukoliko kupi tablet, na cenu
            se primenjuje kupon.
       
    }*/
}
