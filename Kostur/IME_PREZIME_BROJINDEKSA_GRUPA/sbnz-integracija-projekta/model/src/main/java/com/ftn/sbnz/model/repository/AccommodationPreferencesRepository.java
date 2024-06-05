package com.ftn.sbnz.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationPreferencesRepository extends JpaRepository<AccommodationRepository,Long> {
}
