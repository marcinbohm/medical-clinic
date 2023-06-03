package com.mb_medical_clinic_be.security.jwt;

import com.mb_medical_clinic_be.security.domain.CurrentUser;

import java.time.Duration;
import java.util.Optional;

public interface JwtService {

    TokenSubject parseToken(String authToken);

    String generateToken(TokenSubject tokenSubject, Duration expirationInMinutes);

    Optional<CurrentUser> loadUserByToken(String token);
}
