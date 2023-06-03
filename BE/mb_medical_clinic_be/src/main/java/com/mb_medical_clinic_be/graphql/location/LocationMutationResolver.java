package com.mb_medical_clinic_be.graphql.location;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.dict.DictOperationName;
import com.mb_medical_clinic_be.entity.Location;
import com.mb_medical_clinic_be.mapper.SmartMapper;
import com.mb_medical_clinic_be.repository.LocationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class LocationMutationResolver implements GraphQLMutationResolver {

    private final LocationRepository locationRepository;

    public LocationMutationResolver(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Transactional
    @PreAuthorize("hasAuthority('LOCATION_ADD_PRIVILEGE')")
    public OperationStatus addLocation(LocationInput locationInput) {
        return saveLocation(null, locationInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('LOCATION_MODIFY_PRIVILEGE')")
    public OperationStatus updateLocation(@NotNull Integer locationInputId, LocationInput locationInput) {
        return saveLocation(locationInputId, locationInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('LOCATION_DELETE_PRIVILEGE')")
    public OperationStatus deleteLocation(@NotNull Integer locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(EntityNotFoundException::new);

        OperationStatus opStatus = new OperationStatus(Location.class.getSimpleName(), DictOperationName.DELETE.getCode());
        opStatus.setRecordId(locationId);

        locationRepository.delete(location);

        return opStatus.setSuccess(!locationRepository.existsById(locationId));
    }

    protected OperationStatus saveLocation(Integer locationId, LocationInput locationInput) {
        boolean adding = (locationId == null);
        Location location = (adding ? new Location() : locationRepository.findById(locationId).orElseThrow(EntityNotFoundException::new));

        OperationStatus opStatus = new OperationStatus(Location.class.getSimpleName(), adding ? DictOperationName.ADD.getCode() : DictOperationName.UPDATE.getCode());

        SmartMapper.transferData(locationInput, location);

        Location locationSaved = locationRepository.save(location);
        Integer id = locationSaved.getLocationId();
        opStatus.setRecordId(id).setSuccess(id != null);
        return opStatus;
    }
}
