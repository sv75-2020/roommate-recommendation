package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.AccommodationRatingReport;
import com.ftn.sbnz.model.dto.AdminReportDTO;
import com.ftn.sbnz.model.dto.PopularLocationsDTO;
import com.ftn.sbnz.service.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportService;

    @GetMapping()
    public AdminReportDTO getReport(
            @RequestParam("month") int month,
            @RequestParam("year") int year) {
        return reportService.getReport(month, year);
    }

}
