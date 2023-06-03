package com.mb_medical_clinic_be.resource.session;


import com.mb_medical_clinic_be.entity.Session;

import java.util.Optional;

public interface SessionService {

    Optional<Session> findByToken(String token);

    Optional<Session> findByUserId(Integer userId);

    <S extends Session> S save(S session);

    void delete(Session session);
}
