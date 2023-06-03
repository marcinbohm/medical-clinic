package com.mb_medical_clinic_be.security.domain;

public enum LoginType {

    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    LoginType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
