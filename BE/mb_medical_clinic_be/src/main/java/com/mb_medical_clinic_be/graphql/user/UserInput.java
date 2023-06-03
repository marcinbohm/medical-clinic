package com.mb_medical_clinic_be.graphql.user;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserInput {

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

    private boolean locationIdSetFromInput = false;

    private boolean firstnameSetFromInput = false;

    private boolean lastnameSetFromInput = false;

    private boolean emailSetFromInput = false;

    private boolean loginSetFromInput = false;

    private boolean passwordSetFromInput = false;

    private boolean activeSetFromInput = false;

    private boolean blockedSetFromInput = false;

    private boolean expireAccountDateSetFromInput = false;

    private boolean expirePasswordDateSetFromInput = false;

    private boolean lastLoginTimeSetFromInput = false;

    private boolean userTypeSetFromInput = false;

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
        this.locationIdSetFromInput = true;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        this.firstnameSetFromInput = true;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        this.lastnameSetFromInput = true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.emailSetFromInput = true;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        this.loginSetFromInput = true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.passwordSetFromInput = true;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
        this.activeSetFromInput = true;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
        this.blockedSetFromInput = true;
    }

    public LocalDate getExpireAccountDate() {
        return expireAccountDate;
    }

    public void setExpireAccountDate(LocalDate expireAccountDate) {
        this.expireAccountDate = expireAccountDate;
        this.expireAccountDateSetFromInput = true;
    }

    public LocalDate getExpirePasswordDate() {
        return expirePasswordDate;
    }

    public void setExpirePasswordDate(LocalDate expirePasswordDate) {
        this.expirePasswordDate = expirePasswordDate;
        this.expirePasswordDateSetFromInput = true;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        this.lastLoginTimeSetFromInput = true;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
        this.userTypeSetFromInput = true;
    }

    public boolean isLocationIdSetFromInput() {
        return locationIdSetFromInput;
    }

    public boolean isFirstnameSetFromInput() {
        return firstnameSetFromInput;
    }

    public boolean isLastnameSetFromInput() {
        return lastnameSetFromInput;
    }

    public boolean isEmailSetFromInput() {
        return emailSetFromInput;
    }

    public boolean isLoginSetFromInput() {
        return loginSetFromInput;
    }

    public boolean isPasswordSetFromInput() {
        return passwordSetFromInput;
    }

    public boolean isActiveSetFromInput() {
        return activeSetFromInput;
    }

    public boolean isBlockedSetFromInput() {
        return blockedSetFromInput;
    }

    public boolean isExpireAccountDateSetFromInput() {
        return expireAccountDateSetFromInput;
    }

    public boolean isExpirePasswordDateSetFromInput() {
        return expirePasswordDateSetFromInput;
    }

    public boolean isLastLoginTimeSetFromInput() {
        return lastLoginTimeSetFromInput;
    }

    public boolean isUserTypeSetFromInput() {
        return userTypeSetFromInput;
    }
}
