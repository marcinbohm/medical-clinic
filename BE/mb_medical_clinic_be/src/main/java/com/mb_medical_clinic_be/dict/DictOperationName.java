package com.mb_medical_clinic_be.dict;

public enum DictOperationName {
    ADD("add"),
    UPDATE("update"),
    DELETE("delete"),
    VERIFY("verify");

    private final String code;

    private DictOperationName(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
