package com.mb_medical_clinic_be.graphql.location;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mb_medical_clinic_be.entity.Location;
import com.mb_medical_clinic_be.repository.LocationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class LocationQueryResolver implements GraphQLQueryResolver {

    private final LocationRepository locationRepository;

    public LocationQueryResolver(LocationRepository companyRepository) {
        this.locationRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('LOCATION_READ_PRIVILEGE')")
    public Location getLocation(@NotNull Integer locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('LOCATION_READ_PRIVILEGE')")
    public List<Location> getLocationList() {
        return locationRepository.findAll();
    }
}
