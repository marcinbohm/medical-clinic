package com.mb_medical_clinic_be.graphql.category;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class CategoryInput {

    private Integer categoryId;

    private String code;

    private String description;

    private String helpDescription;

    private boolean codeSetFromInput = false;

    private boolean descriptionSetFromInput = false;

    private boolean helpDescriptionSetFromInput = false;

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
        this.codeSetFromInput = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.descriptionSetFromInput = true;
    }

    public String getHelpDescription() {
        return helpDescription;
    }

    public void setHelpDescription(String helpDescription) {
        this.helpDescription = helpDescription;
        this.helpDescriptionSetFromInput = true;
    }

    public boolean isCodeSetFromInput() {
        return codeSetFromInput;
    }

    public boolean isDescriptionSetFromInput() {
        return descriptionSetFromInput;
    }

    public boolean isHelpDescriptionSetFromInput() {
        return helpDescriptionSetFromInput;
    }
}
