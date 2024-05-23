package com.ftn.sbnz.model.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.NotifyAdminForBill;
import com.ftn.sbnz.model.models.UserWarning;
import com.ftn.sbnz.model.repository.AdminRepository;
import com.ftn.sbnz.model.repository.UserWarningRepository;

@Service
public class AdminService {

    @Autowired 
    public AdminRepository adminRepository;

    @Autowired
    public UserWarningRepository userWarningRepository;

    public void sendWarning(NotifyAdminForBill notification){
        UserWarning warning=new UserWarning();
        warning.setDate(LocalDate.now());
        warning.setUser(notification.getUser());
        userWarningRepository.save(warning);
    }
}
