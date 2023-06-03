package com.mb_medical_clinic_be.graphql.examination;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.dict.DictOperationName;
import com.mb_medical_clinic_be.entity.Examination;
import com.mb_medical_clinic_be.mapper.SmartMapper;
import com.mb_medical_clinic_be.repository.ExaminationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class ExaminationMutationResolver implements GraphQLMutationResolver {

    private final ExaminationRepository examinationRepository;

    public ExaminationMutationResolver(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    @Transactional
    @PreAuthorize("hasAuthority('EXAMINATION_ADD_PRIVILEGE')")
    public OperationStatus addExamination(ExaminationInput examinationInput) {
        return saveExamination(null, examinationInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('EXAMINATION_MODIFY_PRIVILEGE')")
    public OperationStatus updateExamination(@NotNull Integer examinationInputId, ExaminationInput examinationInput) {
        return saveExamination(examinationInputId, examinationInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('EXAMINATION_DELETE_PRIVILEGE')")
    public OperationStatus deleteExamination(@NotNull Integer examinationId) {
        Examination examination = examinationRepository.findById(examinationId).orElseThrow(EntityNotFoundException::new);

        OperationStatus opStatus = new OperationStatus(Examination.class.getSimpleName(), DictOperationName.DELETE.getCode());
        opStatus.setRecordId(examinationId);

        examinationRepository.delete(examination);

        return opStatus.setSuccess(!examinationRepository.existsById(examinationId));
    }

    protected OperationStatus saveExamination(Integer examinationId, ExaminationInput examinationInput) {
        boolean adding = (examinationId == null);
        Examination examination = (adding ? new Examination() : examinationRepository.findById(examinationId).orElseThrow(EntityNotFoundException::new));

        OperationStatus opStatus = new OperationStatus(Examination.class.getSimpleName(), adding ? DictOperationName.ADD.getCode() : DictOperationName.UPDATE.getCode());

        SmartMapper.transferData(examinationInput, examination);

        Examination examinationSaved = examinationRepository.save(examination);
        Integer id = examinationSaved.getExaminationId();
        opStatus.setRecordId(id).setSuccess(id != null);
        return opStatus;
    }
}
