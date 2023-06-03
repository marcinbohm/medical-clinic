package com.mb_medical_clinic_be.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

public class SortOrder {
    private Sort.Direction direction;
    private String property;

    public SortOrder() {
    }

    public Sort.Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        if (StringUtils.isAlphanumeric(property)) {
            this.property = property;
        }

    }
}
