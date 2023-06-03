package com.mb_medical_clinic_be.repository;

import com.mb_medical_clinic_be.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Integer> {
    List<Class> findByClassUserSet_UserId(Integer userId);
}
