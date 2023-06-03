package com.mb_medical_clinic_be.dto;

import java.util.Objects;

public class Privilege {
    private String name;
    private Boolean allowRead;
    private Boolean allowAdd;
    private Boolean allowModify;
    private Boolean allowDelete;

    public Privilege(String name, Boolean allowRead, Boolean allowAdd, Boolean allowModify, Boolean allowDelete) {
        this.name = name;
        this.allowRead = allowRead;
        this.allowAdd = allowAdd;
        this.allowModify = allowModify;
        this.allowDelete = allowDelete;
    }

    public String getName() {
        return this.name;
    }

    public Privilege setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getAllowRead() {
        return this.allowRead;
    }

    public Privilege setAllowRead(Boolean allowRead) {
        this.allowRead = allowRead;
        return this;
    }

    public Boolean getAllowAdd() {
        return this.allowAdd;
    }

    public Privilege setAllowAdd(Boolean allowAdd) {
        this.allowAdd = allowAdd;
        return this;
    }

    public Boolean getAllowModify() {
        return this.allowModify;
    }

    public Privilege setAllowModify(Boolean allowModify) {
        this.allowModify = allowModify;
        return this;
    }

    public Boolean getAllowDelete() {
        return this.allowDelete;
    }

    public Privilege setAllowDelete(Boolean allowDelete) {
        this.allowDelete = allowDelete;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Privilege)) {
            return false;
        } else {
            Privilege privilege = (Privilege)o;
            return this.getName().equals(privilege.getName()) && Objects.equals(this.getAllowRead(), privilege.getAllowRead()) && Objects.equals(this.getAllowAdd(), privilege.getAllowAdd()) && Objects.equals(this.getAllowModify(), privilege.getAllowModify()) && Objects.equals(this.getAllowDelete(), privilege.getAllowDelete());
        }
    }

    public int hashCode() {
        return Objects.hash(this.getName(), this.getAllowRead(), this.getAllowAdd(), this.getAllowModify(), this.getAllowDelete());
    }
}
