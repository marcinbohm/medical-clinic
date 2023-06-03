package com.mb_medical_clinic_be.security.domain;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CurrentUser implements UserDetails {
    private String jwtToken;
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
    private Boolean enabled;
    private Boolean locked;
    private LocalDate accountExpired;
    private LocalDate passwordExpired;
    private LocalDateTime lastLoginTime;
    private Integer userType;
    private final Set<GrantedAuthority> grantedAuthorities = new HashSet();

    public CurrentUser() {
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public CurrentUser setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }

    public Integer getId() {
        return this.id;
    }

    public CurrentUser setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public CurrentUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public CurrentUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public CurrentUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getLogin() {
        return this.login;
    }

    public CurrentUser setLogin(String login) {
        this.login = login;
        return this;
    }

    public CurrentUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public CurrentUser setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public CurrentUser setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public LocalDate getAccountExpired() {
        return this.accountExpired;
    }

    public CurrentUser setAccountExpired(LocalDate accountExpired) {
        this.accountExpired = accountExpired;
        return this;
    }

    public LocalDate getPasswordExpired() {
        return this.passwordExpired;
    }

    public CurrentUser setPasswordExpired(LocalDate passwordExpired) {
        this.passwordExpired = passwordExpired;
        return this;
    }

    public LocalDateTime getLastLoginTime() {
        return this.lastLoginTime;
    }

    public CurrentUser setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public CurrentUser addGrantedAuthority(GrantedAuthority authority) {
        this.grantedAuthorities.add(authority);
        return this;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(this.grantedAuthorities);
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.login;
    }

    public boolean isAccountNonExpired() {
        return this.getAccountExpired() == null || this.getAccountExpired().isAfter(LocalDate.now());
    }

    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    public boolean isCredentialsNonExpired() {
        return this.getPasswordExpired() == null || this.getPasswordExpired().isAfter(LocalDate.now());
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isUser() {
        return this.grantedAuthorities.stream().anyMatch((authority) -> StringUtils.equals("ROLE_USER", authority.getAuthority()));
    }

    public boolean isAdmin() {
        return this.grantedAuthorities.stream().anyMatch((authority) -> StringUtils.equals("ROLE_ADMIN", authority.getAuthority()));
    }
}
