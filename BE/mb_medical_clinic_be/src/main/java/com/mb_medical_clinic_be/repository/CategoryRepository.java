package com.mb_medical_clinic_be.repository;

import com.mb_medical_clinic_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
