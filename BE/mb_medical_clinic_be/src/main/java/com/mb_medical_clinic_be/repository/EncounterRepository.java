package com.mb_medical_clinic_be.repository;

import com.mb_medical_clinic_be.entity.Encounter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncounterRepository extends JpaRepository<Encounter, Integer> {
    Page<Encounter> findByLocationId(Integer locationId, Pageable pageable);

    Page<Encounter> findByLocationIdAndPatient_UserType(Integer locationId, Integer userType, Pageable pageable);
}
