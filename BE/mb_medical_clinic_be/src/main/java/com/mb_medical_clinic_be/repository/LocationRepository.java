package com.mb_medical_clinic_be.repository;

import com.mb_medical_clinic_be.entity.Examination;
import com.mb_medical_clinic_be.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
