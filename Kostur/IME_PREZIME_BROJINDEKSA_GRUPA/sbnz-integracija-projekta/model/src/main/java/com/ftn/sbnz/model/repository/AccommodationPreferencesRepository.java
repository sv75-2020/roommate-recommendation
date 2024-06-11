package com.ftn.sbnz.model.repository;

import com.ftn.sbnz.model.models.AccommodationPreferences;
import com.ftn.sbnz.model.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationPreferencesRepository extends JpaRepository<AccommodationPreferences,Long> {
    AccommodationPreferences findByUser(User roommate2);
}
