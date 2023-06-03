package com.mb_medical_clinic_be.repository;

import com.mb_medical_clinic_be.entity.Permission;
import com.mb_medical_clinic_be.dto.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("SELECT new com.mb_medical_clinic_be.dto.Privilege(lc.code, true, true, true, true) FROM Category lc")
    List<Privilege> findAllAdminPrivileges();

    List<Permission> findByPermissionClass_ClassUserSet_UserId(Integer userId);

}
