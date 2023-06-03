package com.mb_medical_clinic_be.security.jwt;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

public enum BearerType {
    CURRENT_USER ("H"),
    REFRESH_CURRENT_USER_TOKEN("HR");

    private final String type;

    BearerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static BearerType findByValue(String type){
        return Stream.of(BearerType.values())
                .filter(e -> StringUtils.equals(e.type, type))
                .findFirst()
                .orElse(null);
    }
}
