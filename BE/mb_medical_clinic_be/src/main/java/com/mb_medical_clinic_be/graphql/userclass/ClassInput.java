package com.mb_medical_clinic_be.graphql.userclass;

public class ClassInput {

    private String classId;

    private String classCode;

    private String name;

    private String description;

    private Boolean adminClass;

    private Boolean active;

    private boolean classCodeSetFromInput = false;

    private boolean nameSetFromInput = false;

    private boolean descriptionSetFromInput = false;

    private boolean adminClassSetFromInput = false;

    private boolean activeSetFromInput = false;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
        this.classCodeSetFromInput = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameSetFromInput = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.descriptionSetFromInput = true;
    }

    public Boolean getAdminClass() {
        return adminClass;
    }

    public void setAdminClass(Boolean adminClass) {
        this.adminClass = adminClass;
        this.adminClassSetFromInput = true;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
        this.activeSetFromInput = true;
    }

    public boolean isClassCodeSetFromInput() {
        return classCodeSetFromInput;
    }

    public boolean isNameSetFromInput() {
        return nameSetFromInput;
    }

    public boolean isDescriptionSetFromInput() {
        return descriptionSetFromInput;
    }

    public boolean isAdminClassSetFromInput() {
        return adminClassSetFromInput;
    }

    public boolean isActiveSetFromInput() {
        return activeSetFromInput;
    }
}
