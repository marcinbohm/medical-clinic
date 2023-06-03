package com.mb_medical_clinic_be.graphql.encounter;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.dict.DictOperationName;
import com.mb_medical_clinic_be.entity.Encounter;
import com.mb_medical_clinic_be.mapper.SmartMapper;
import com.mb_medical_clinic_be.repository.EncounterRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class EncounterMutationResolver implements GraphQLMutationResolver {

    private final EncounterRepository encounterRepository;

    public EncounterMutationResolver(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    @Transactional
    @PreAuthorize("hasAuthority('ENCOUNTER_ADD_PRIVILEGE')")
    public OperationStatus addEncounter(EncounterInput encounterInput) {
        return saveEncounter(null, encounterInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ENCOUNTER_MODIFY_PRIVILEGE')")
    public OperationStatus updateEncounter(@NotNull Integer encounterInputId, EncounterInput encounterInput) {
        return saveEncounter(encounterInputId, encounterInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ENCOUNTER_DELETE_PRIVILEGE')")
    public OperationStatus deleteEncounter(@NotNull Integer encounterId) {
        Encounter encounter = encounterRepository.findById(encounterId).orElseThrow(EntityNotFoundException::new);

        OperationStatus opStatus = new OperationStatus(Encounter.class.getSimpleName(), DictOperationName.DELETE.getCode());
        opStatus.setRecordId(encounterId);

        encounterRepository.delete(encounter);

        return opStatus.setSuccess(!encounterRepository.existsById(encounterId));
    }

    protected OperationStatus saveEncounter(Integer encounterId, EncounterInput encounterInput) {
        boolean adding = (encounterId == null);
        Encounter encounter = (adding ? new Encounter() : encounterRepository.findById(encounterId).orElseThrow(EntityNotFoundException::new));

        OperationStatus opStatus = new OperationStatus(Encounter.class.getSimpleName(), adding ? DictOperationName.ADD.getCode() : DictOperationName.UPDATE.getCode());

        SmartMapper.transferData(encounterInput, encounter);

        Encounter encounterSaved = encounterRepository.save(encounter);
        Integer id = encounterSaved.getEncounterId();
        opStatus.setRecordId(id).setSuccess(id != null);
        return opStatus;
    }
}
