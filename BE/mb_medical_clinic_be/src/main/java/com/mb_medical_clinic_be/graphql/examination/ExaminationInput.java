package com.mb_medical_clinic_be.graphql.examination;

import java.time.LocalDateTime;

public class ExaminationInput {

    private Integer examinationId;

    private Integer encounterId;

    private String name;

    private LocalDateTime executionDate;

    private String description;

    private String icd9;

    private LocalDateTime createdAt;

    private Integer createdBy;

    private LocalDateTime updatedAt;

    private Integer updatedBy;

    private boolean examinationIdSetFromInput = false;

    private boolean encounterIdSetFromInput = false;

    private boolean nameSetFromInput = false;

    private boolean executionDateSetFromInput = false;

    private boolean descriptionSetFromInput = false;

    private boolean icd9SetFromInput = false;

    private boolean createdAtSetFromInput = false;

    private boolean createdBySetFromInput = false;

    private boolean updatedAtSetFromInput = false;

    private boolean updatedBySetFromInput = false;

    public Integer getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Integer examinationId) {
        this.examinationId = examinationId;
        this.examinationIdSetFromInput = true;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
        this.encounterIdSetFromInput = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameSetFromInput = true;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
        this.executionDateSetFromInput = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.descriptionSetFromInput = true;
    }

    public String getIcd9() {
        return icd9;
    }

    public void setIcd9(String icd9) {
        this.icd9 = icd9;
        this.icd9SetFromInput = true;
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

    public boolean isExaminationIdSetFromInput() {
        return examinationIdSetFromInput;
    }

    public boolean isEncounterIdSetFromInput() {
        return encounterIdSetFromInput;
    }

    public boolean isNameSetFromInput() {
        return nameSetFromInput;
    }

    public boolean isExecutionDateSetFromInput() {
        return executionDateSetFromInput;
    }

    public boolean isDescriptionSetFromInput() {
        return descriptionSetFromInput;
    }

    public boolean isIcd9SetFromInput() {
        return icd9SetFromInput;
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
