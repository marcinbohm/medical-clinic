package com.mb_medical_clinic_be.graphql.encounter;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mb_medical_clinic_be.common.SortOrder;
import com.mb_medical_clinic_be.common.SortParamMapper;
import com.mb_medical_clinic_be.dict.DictUserType;
import com.mb_medical_clinic_be.entity.Encounter;
import com.mb_medical_clinic_be.repository.EncounterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class EncounterQueryResolver implements GraphQLQueryResolver {

    private final EncounterRepository encounterRepository;

    public EncounterQueryResolver(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ENCOUNTER_READ_PRIVILEGE')")
    public Encounter getEncounter(@NotNull Integer encounterId) {
        return encounterRepository.findById(encounterId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ENCOUNTER_READ_PRIVILEGE')")
    public Page<Encounter> getEncounterPageByLocationId(@NotNull Integer locationId,
                                                        @NotNull @Min(0) Integer page, @NotNull @Min(0) @Max(100) Integer size, List<SortOrder> orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortParamMapper.map(orderBy)));
        return encounterRepository.findByLocationId(locationId, pageable);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ENCOUNTER_READ_PRIVILEGE')")
    public Page<Encounter> getEncounterPageByLocationIdAndUserPatient(@NotNull Integer locationId,
                                                        @NotNull @Min(0) Integer page, @NotNull @Min(0) @Max(100) Integer size, List<SortOrder> orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortParamMapper.map(orderBy)));
        return encounterRepository.findByLocationIdAndPatient_UserType(locationId, DictUserType.PATIENT.getCode(), pageable);
    }
}
