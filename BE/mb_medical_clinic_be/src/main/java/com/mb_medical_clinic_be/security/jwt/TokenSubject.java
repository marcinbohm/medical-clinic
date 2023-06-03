package com.mb_medical_clinic_be.security.jwt;

public class TokenSubject {
    private final BearerType bearerType;
    private final Integer userId;

    public TokenSubject(BearerType bearerType, Integer userId) {
        this.bearerType = bearerType;
        this.userId = userId;
    }

    public BearerType getBearerType() {
        return bearerType;
    }

    public Integer getUserId() {
        return userId;
    }
}
