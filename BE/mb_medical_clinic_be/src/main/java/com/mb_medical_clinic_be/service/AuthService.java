package com.mb_medical_clinic_be.service;

import com.mb_medical_clinic_be.entity.Session;
import com.mb_medical_clinic_be.security.domain.AuthSession;
import com.mb_medical_clinic_be.security.domain.RefreshToken;

public interface AuthService {

    AuthSession login(String userName, String password);

    AuthSession refreshToken(RefreshToken refreshToken);

    void logout();
}
