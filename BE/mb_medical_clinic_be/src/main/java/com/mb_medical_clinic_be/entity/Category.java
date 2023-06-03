package com.mb_medical_clinic_be.entity;

import org.springframework.boot.actuate.audit.listener.AuditListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "category")
@EntityListeners(AuditListener.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    @SequenceGenerator(name="archiwum_kontakt_generator", sequenceName = "category_category_id_seq", allocationSize=1)
    @Column(name = "category_id")
    private Integer categoryId;

    @NotNull
    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "help_description")
    private String helpDescription;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Permission> categoryPermissionsList;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHelpDescription() {
        return helpDescription;
    }

    public void setHelpDescription(String helpDescription) {
        this.helpDescription = helpDescription;
    }

    public List<Permission> getCategoryPermissionsList() {
        return categoryPermissionsList;
    }

    public void setCategoryPermissionsList(List<Permission> categoryPermissionsList) {
        this.categoryPermissionsList = categoryPermissionsList;
    }
}
