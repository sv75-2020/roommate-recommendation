package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.AdminReportDTO;
import com.ftn.sbnz.model.repository.DepositNotPaidRepository;
import com.ftn.sbnz.service.services.EventService;
import com.ftn.sbnz.service.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/deposit")
    public void checkDeposit() {
        eventService.checkDeposit();
    }
    @GetMapping("/bills")
    public void checkBills() {
        eventService.checkBills();
    }
}
