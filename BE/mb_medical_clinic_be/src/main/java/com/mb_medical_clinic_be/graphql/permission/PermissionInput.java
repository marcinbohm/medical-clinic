package com.mb_medical_clinic_be.graphql.permission;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class PermissionInput {

    private Integer permissionId;

    private Integer categoryId;

    private Boolean allowRead;

    private Boolean allowAdd;

    private Boolean allowModify;

    private Boolean allowDelete;

    private Integer classId;

    private boolean categoryIdSetFromInput = false;

    private boolean allowReadSetFromInput = false;

    private boolean allowAddSetFromInput = false;

    private boolean allowModifySetFromInput = false;

    private boolean allowDeleteSetFromInput = false;

    private boolean classIdSetFromInput = false;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        this.categoryIdSetFromInput = true;
    }

    public Boolean getAllowRead() {
        return allowRead;
    }

    public void setAllowRead(Boolean allowRead) {
        this.allowRead = allowRead;
        this.allowReadSetFromInput = true;
    }

    public Boolean getAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(Boolean allowAdd) {
        this.allowAdd = allowAdd;
        this.allowReadSetFromInput = true;
    }

    public Boolean getAllowModify() {
        return allowModify;
    }

    public void setAllowModify(Boolean allowModify) {
        this.allowModify = allowModify;
        this.allowModifySetFromInput = true;
    }

    public Boolean getAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        this.allowDelete = allowDelete;
        this.allowDeleteSetFromInput = true;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public boolean isCategoryIdSetFromInput() {
        return categoryIdSetFromInput;
    }

    public boolean isAllowReadSetFromInput() {
        return allowReadSetFromInput;
    }

    public boolean isAllowAddSetFromInput() {
        return allowAddSetFromInput;
    }

    public boolean isAllowModifySetFromInput() {
        return allowModifySetFromInput;
    }

    public boolean isAllowDeleteSetFromInput() {
        return allowDeleteSetFromInput;
    }

    public boolean isClassIdSetFromInput() {
        return classIdSetFromInput;
    }
}
