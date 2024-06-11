package com.ftn.sbnz.service.services;
import java.util.List;

import com.ftn.sbnz.model.events.NotifyAdminForBillEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.NotifyAdminForBill;
import com.ftn.sbnz.model.repository.NotifyAdminForBillRepository;

@Service
public class NotifyAdminForBillService {
    @Autowired
    public NotifyAdminForBillRepository notifyAdminForBillRepository;

    public List<NotifyAdminForBillEvent> getNotifications(){
        return notifyAdminForBillRepository.findAll();
    }
}
