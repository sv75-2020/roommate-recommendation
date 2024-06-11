package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.dto.AccommodationRatingReport;
import com.ftn.sbnz.model.dto.AdminReportDTO;
import com.ftn.sbnz.model.dto.PopularLocationsDTO;
import com.ftn.sbnz.model.models.Admin;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.repository.AccommodationRepository;
import com.ftn.sbnz.model.repository.AccommodationReviewRepository;
import com.ftn.sbnz.model.repository.LocationRepository;
import com.ftn.sbnz.model.repository.ReservationRepository;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportsService {
        @Autowired
        private LocationRepository locationRepository;
        @Autowired
        private ReservationRepository reservationRepository;
        @Autowired
        private AccommodationRepository accommodationRepository;
        @Autowired
        private AccommodationReviewRepository accommodationReviewRepository;

        public AdminReportDTO getReport(int month, int year) {
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();

            AdminReportDTO report = new AdminReportDTO(); //This is a DTO for all Reports
            KieSession kSession = kContainer.newKieSession("cepKsession");
            locationRepository.findAll().forEach(kSession::insert);
            reservationRepository.findAll().forEach(kSession::insert);
            accommodationRepository.findAll().forEach(kSession::insert);
            accommodationReviewRepository.findAll().forEach(kSession::insert);

            QueryResults results1 = kSession.getQueryResults("getMostPopularLocationsForMonth", month, year);
            report.setPopularLocationsDTO(extractPopularLocationsFromQueryResults(results1));

            QueryResults results2 = kSession.getQueryResults("findAverageByLocation");
            report.setAccommodationRatingReport(extractAccommodationRating(results2));

            QueryResults results3 = kSession.getQueryResults("calculateMonthlyEarnings", year, month);
            report.setTotalEarnings(extractTotalEarnings(results3));

            return report;
        }

    private Double extractTotalEarnings(QueryResults results) {
        double totalEarnings = 0.0;
        for (QueryResultsRow row : results) {
            totalEarnings += (Double) row.get("$totalEarnings");
        }
        return totalEarnings;
    }

    private List<AccommodationRatingReport> extractAccommodationRating(QueryResults results) {
        List<AccommodationRatingReport> resultList = new ArrayList<>();
        for (QueryResultsRow row : results) {
            String locationName = (String) row.get("$locationName");
            Double count = (Double) row.get("$averageRating");
            resultList.add(new AccommodationRatingReport(locationName, count));
        }
        return resultList;
    }

    private List<PopularLocationsDTO> extractPopularLocationsFromQueryResults(QueryResults results) {
            List<PopularLocationsDTO> popularLocations = new ArrayList<>();
            for (QueryResultsRow row : results) {
                String locationName = (String) row.get("$locationName");
                Long count = (Long) row.get("$count");
                popularLocations.add(new PopularLocationsDTO(locationName, count));
            }
            return popularLocations.stream()
                    .sorted(Comparator.comparingLong(PopularLocationsDTO::getCount).reversed())
                    .limit(3)
                    .collect(Collectors.toList());
        }



}
