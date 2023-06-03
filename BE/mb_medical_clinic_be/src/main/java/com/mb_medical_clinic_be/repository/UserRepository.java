package com.mb_medical_clinic_be.repository;

import com.mb_medical_clinic_be.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(Integer userId);

    Optional<User> findByLogin(String login);

    Optional<User> findByUserIdAndUserSessionsList_RefreshTicket(Integer userId, String refreshTicket);

    Page<User> findByLocation_LocationIdAndUserType(Integer locationId, Integer userType, Pageable pageable);

    List<User> findByUserTypeIsNot(Integer userType);

}
