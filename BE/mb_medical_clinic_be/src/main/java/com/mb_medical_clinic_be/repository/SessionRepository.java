package com.mb_medical_clinic_be.repository;

import com.mb_medical_clinic_be.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findByTicket(String ticket);

    Optional<Session> findByUserId(Integer userId);
}
