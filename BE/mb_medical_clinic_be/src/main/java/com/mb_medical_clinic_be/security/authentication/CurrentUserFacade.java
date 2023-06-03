package com.mb_medical_clinic_be.security.authentication;

import com.mb_medical_clinic_be.entity.User;
import com.mb_medical_clinic_be.security.domain.CurrentUser;

public interface CurrentUserFacade {

    CurrentUser convertToCurrentUser(User user);
}
