package com.mb_medical_clinic_be.graphql.examination;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mb_medical_clinic_be.common.SortOrder;
import com.mb_medical_clinic_be.common.SortParamMapper;
import com.mb_medical_clinic_be.entity.Encounter;
import com.mb_medical_clinic_be.entity.Examination;
import com.mb_medical_clinic_be.repository.ExaminationRepository;
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
public class ExaminationQueryResolver implements GraphQLQueryResolver {

    private final ExaminationRepository examinationRepository;

    public ExaminationQueryResolver(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('EXAMINATION_READ_PRIVILEGE')")
    public Examination getExamination(@NotNull Integer examinationId) {
        return examinationRepository.findById(examinationId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ENCOUNTER_READ_PRIVILEGE')")
    public Page<Examination> getExaminationPageByLocationId(@NotNull Integer locationId,
                                                        @NotNull @Min(0) Integer page, @NotNull @Min(0) @Max(100) Integer size, List<SortOrder> orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortParamMapper.map(orderBy)));
        return examinationRepository.findByEncounter_LocationId(locationId, pageable);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ENCOUNTER_READ_PRIVILEGE')")
    public Page<Examination> getExaminationPageByEncounterId(@NotNull Integer encounterId,
                                                            @NotNull @Min(0) Integer page, @NotNull @Min(0) @Max(100) Integer size, List<SortOrder> orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortParamMapper.map(orderBy)));
        return examinationRepository.findByEncounterId(encounterId, pageable);
    }
}
