package com.mb_medical_clinic_be.dict;

public enum DictUserType {
    ADMIN("Administrator", 1),
    NURSE("Nurse", 2),
    DOCTOR("Doctor", 3),
    REGISTRATOR("Registrator", 4),
    PATIENT("Patient", 5);

    private final String description;
    private final Integer code;

    private DictUserType(String description, Integer code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }
    public Integer getCode(){return this.code;}
}
