package com.mb_medical_clinic_be.resource.user.registration.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistrationDTO {

    private Integer userId;

    private Integer locationId;

    private String firstname;

    private String lastname;

    private String email;

    private String login;

    private String password;

    private Boolean active;

    private Boolean blocked;

    private LocalDate expireAccountDate;

    private LocalDate expirePasswordDate;

    private LocalDateTime lastLoginTime;

    private Integer userType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public LocalDate getExpireAccountDate() {
        return expireAccountDate;
    }

    public void setExpireAccountDate(LocalDate expireAccountDate) {
        this.expireAccountDate = expireAccountDate;
    }

    public LocalDate getExpirePasswordDate() {
        return expirePasswordDate;
    }

    public void setExpirePasswordDate(LocalDate expirePasswordDate) {
        this.expirePasswordDate = expirePasswordDate;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
