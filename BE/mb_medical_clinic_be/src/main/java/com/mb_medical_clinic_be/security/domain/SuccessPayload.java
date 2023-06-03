package com.mb_medical_clinic_be.security.domain;

public class SuccessPayload {
    
    private final String success;

    public SuccessPayload(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }
}
