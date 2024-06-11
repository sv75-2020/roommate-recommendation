package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.repository.*;
import enums.*;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private DepositNotPaidRepository depositNotPaidRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private NotifyAdminEvictionRepository notifyAdminEvictionRepository;
    @Autowired
    private MonthlyPaymentRepository monthlyPaymentRepository;
    @Autowired
    private UserWarningRepository userWarningRepository;
    @Autowired
    private NotifyAdminForBillRepository notifyAdminForBillRepository;
    @Autowired
    private BillPaidRepository billPaidRepository;

    public void checkDeposit() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("cepKsession");
        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.setGlobal("depositNotPaidRepository", depositNotPaidRepository);
        ksession.setGlobal("reservationRepository", reservationRepository);
        ksession.setGlobal("userRepository", userRepository);
        ksession.setGlobal("notifyAdminEvictionRepository", notifyAdminEvictionRepository);
        reservationRepository.findAll().forEach(ksession::insert);
        userRepository.findAll().forEach(ksession::insert);
        depositNotPaidRepository.findAll().forEach(ksession::insert);
        ksession.getAgenda().getAgendaGroup("deposit-cep").setFocus();
        ksession.fireAllRules();
        ksession.dispose();
    }

    public void checkBills() {
        KieServices ks = KieServices.Factory.get();

        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("cepKsession");
        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.setGlobal("userWarningRepository", userWarningRepository);
        userWarningRepository.findAll().forEach(ksession::insert);
        ksession.setGlobal("userRepository", userRepository);
        userRepository.findAll().forEach(ksession::insert);
        ksession.setGlobal("notifyAdminEvictionRepository", notifyAdminEvictionRepository);
        ksession.setGlobal("notifyAdminForBillRepository", notifyAdminForBillRepository);
        monthlyPaymentRepository.findAll().forEach(ksession::insert);
        billPaidRepository.findAll().forEach(ksession::insert);
        billPaidRepository.findAll().forEach(e->System.out.println(e.getPaymentDate()));
        ksession.getAgenda().getAgendaGroup("bills-cep").setFocus();
        ksession.fireAllRules();
        ksession.dispose();
    }
}
