package com.mb_medical_clinic_be.repository;

import com.mb_medical_clinic_be.entity.Encounter;
import com.mb_medical_clinic_be.entity.Examination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminationRepository extends JpaRepository<Examination, Integer> {
    Page<Examination> findByEncounter_LocationId(Integer locationId, Pageable pageable);

    Page<Examination> findByEncounterId(Integer encounterId, Pageable pageable);
}
