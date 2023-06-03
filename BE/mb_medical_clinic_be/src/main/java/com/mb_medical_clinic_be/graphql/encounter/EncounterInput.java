package com.mb_medical_clinic_be.graphql.encounter;

import java.time.LocalDateTime;

public class EncounterInput {

    private Integer encounterId;

    private Integer patientId;

    private Integer practitionerId;

    private String description;

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;

    private Integer locationId;

    private String icd10;

    private LocalDateTime createdAt;

    private Integer createdBy;

    private LocalDateTime updatedAt;

    private Integer updatedBy;

    private boolean encounterSetFromInput = false;

    private boolean patientIdSetFromInput = false;

    private boolean practitionerIdSetFromInput = false;

    private boolean descriptionSetFromInput = false;

    private boolean dateFromSetFromInput = false;

    private boolean dateToSetFromInput = false;

    private boolean locationIdSetFromInput = false;

    private boolean icd10SetFromInput = false;

    private boolean createdAtSetFromInput = false;

    private boolean createdBySetFromInput = false;

    private boolean updatedAtSetFromInput = false;

    private boolean updatedBySetFromInput = false;

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
        this.encounterSetFromInput = true;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
        this.patientIdSetFromInput = true;
    }

    public Integer getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(Integer practitionerId) {
        this.practitionerId = practitionerId;
        this.practitionerIdSetFromInput = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.descriptionSetFromInput = true;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
        this.dateFromSetFromInput = true;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
        this.dateToSetFromInput = true;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
        this.locationIdSetFromInput = true;
    }

    public String getIcd10() {
        return icd10;
    }

    public void setIcd10(String icd10) {
        this.icd10 = icd10;
        this.icd10SetFromInput = true;
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

    public boolean isEncounterSetFromInput() {
        return encounterSetFromInput;
    }

    public boolean isPatientIdSetFromInput() {
        return patientIdSetFromInput;
    }

    public boolean isPractitionerIdSetFromInput() {
        return practitionerIdSetFromInput;
    }

    public boolean isDescriptionSetFromInput() {
        return descriptionSetFromInput;
    }

    public boolean isDateFromSetFromInput() {
        return dateFromSetFromInput;
    }

    public boolean isDateToSetFromInput() {
        return dateToSetFromInput;
    }

    public boolean isLocationIdSetFromInput() {
        return locationIdSetFromInput;
    }

    public boolean isIcd10SetFromInput() {
        return icd10SetFromInput;
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
