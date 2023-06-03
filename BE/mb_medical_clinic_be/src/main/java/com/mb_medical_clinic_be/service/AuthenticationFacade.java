package com.mb_medical_clinic_be.service;

import com.mb_medical_clinic_be.security.domain.CurrentUser;

public interface AuthenticationFacade {
    boolean isCurrentUserAuthenticated();

    CurrentUser getCurrentUser();
}
