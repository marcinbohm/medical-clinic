package com.mb_medical_clinic_be.resource.user.registration;

import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.resource.user.registration.model.RegistrationDTO;

public interface RegistrationService {
    OperationStatus registerUser(RegistrationDTO registrationDTO);
}
