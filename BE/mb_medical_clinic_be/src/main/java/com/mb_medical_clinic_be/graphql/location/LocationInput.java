package com.mb_medical_clinic_be.graphql.location;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class LocationInput {

    private Integer locationId;

    private String name;

    private String postalCode;

    private String city;

    private String street;

    private LocalDateTime createdAt;

    private Integer createdBy;

    private LocalDateTime updatedAt;

    private Integer updatedBy;

    private boolean locationIdSetFromInput = false;

    private boolean nameSetFromInput = false;

    private boolean postalCodeSetFromInput = false;

    private boolean citySetFromInput = false;

    private boolean streetSetFromInput = false;

    private boolean createdAtSetFromInput = false;

    private boolean createdBySetFromInput = false;

    private boolean updatedAtSetFromInput = false;

    private boolean updatedBySetFromInput = false;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameSetFromInput = true;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.postalCodeSetFromInput = true;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        this.citySetFromInput = true;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
        this.streetSetFromInput = true;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.createdAtSetFromInput = true;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        this.createdBySetFromInput = true;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        this.updatedAtSetFromInput = true;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedBySetFromInput = true;
    }

    public boolean isLocationIdSetFromInput() {
        return locationIdSetFromInput;
    }

    public boolean isNameSetFromInput() {
        return nameSetFromInput;
    }

    public boolean isPostalCodeSetFromInput() {
        return postalCodeSetFromInput;
    }

    public boolean isCitySetFromInput() {
        return citySetFromInput;
    }

    public boolean isStreetSetFromInput() {
        return streetSetFromInput;
    }

    public boolean isCreatedAtSetFromInput() {
        return createdAtSetFromInput;
    }

    public boolean isCreatedBySetFromInput() {
        return createdBySetFromInput;
    }

    public boolean isUpdatedAtSetFromInput() {
        return updatedAtSetFromInput;
    }

    public boolean isUpdatedBySetFromInput() {
        return updatedBySetFromInput;
    }
}
