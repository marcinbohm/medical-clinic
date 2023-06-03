package com.mb_medical_clinic_be.entity;

import jakarta.validation.constraints.Pattern;
import org.springframework.boot.actuate.audit.listener.AuditListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "location")
@EntityListeners(AuditListener.class)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_generator")
    @SequenceGenerator(name="location_generator", sequenceName = "location_location_id_seq", allocationSize=1)
    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "name")
    private String name;

    @Column(name = "postal_code")
    @Pattern(regexp = "\\d{2}-\\d{3}")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<User> locationUsersList;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<User> getLocationUsersList() {
        return locationUsersList;
    }

    public void setLocationUsersList(List<User> locationUsersList) {
        this.locationUsersList = locationUsersList;
    }
}
