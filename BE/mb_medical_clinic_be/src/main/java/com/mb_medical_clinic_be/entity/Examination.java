package com.mb_medical_clinic_be.entity;


import org.springframework.boot.actuate.audit.listener.AuditListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "examination")
@EntityListeners(AuditListener.class)
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examination_generator")
    @SequenceGenerator(name="examination_generator", sequenceName = "examination_examination_id_seq", allocationSize=1)
    @Column(name = "examination_id")
    private Integer examinationId;

    @NotNull
    @Column(name = "encounter_id")
    private Integer encounterId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "execution_date")
    private LocalDateTime executionDate;

    @Column(name = "description")
    private String description;

    @Column(name = "icd9")
    private String icd9;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "encounter_id",
            insertable = false,
            updatable = false
    )
    private Encounter encounter;

    public Integer getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Integer examinationId) {
        this.examinationId = examinationId;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcd9() {
        return icd9;
    }

    public void setIcd9(String icd9) {
        this.icd9 = icd9;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }
}
