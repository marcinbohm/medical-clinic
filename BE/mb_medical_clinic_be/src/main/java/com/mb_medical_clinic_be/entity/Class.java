package com.mb_medical_clinic_be.entity;

import org.springframework.boot.actuate.audit.listener.AuditListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "class")
@EntityListeners(AuditListener.class)
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_generator")
    @SequenceGenerator(name="class_generator", sequenceName = "class_class_id_seq", allocationSize=1)
    @Column(name = "class_id")
    private Integer classId;

    @NotNull
    @Column(name = "class_code")
    private String classCode;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean adminClass;

    @Column(name = "admin_class")
    private Boolean active;

    @OneToMany(mappedBy = "permissionClass", fetch = FetchType.LAZY)
    private List<Permission> classPermissionList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_class",
            joinColumns = { @JoinColumn(name = "class_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> classUserSet;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Boolean getAdminClass() {
        return adminClass;
    }

    public void setAdminClass(Boolean adminClass) {
        this.adminClass = adminClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Permission> getClassPermissionList() {
        return classPermissionList;
    }

    public void setClassPermissionList(List<Permission> classPermissionList) {
        this.classPermissionList = classPermissionList;
    }

    public Set<User> getClassUserSet() {
        return classUserSet;
    }

    public void setClassUserSet(Set<User> classUserSet) {
        this.classUserSet = classUserSet;
    }
}
