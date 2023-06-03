package com.mb_medical_clinic_be.graphql.userclass;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.dict.DictOperationName;
import com.mb_medical_clinic_be.entity.Class;
import com.mb_medical_clinic_be.mapper.SmartMapper;
import com.mb_medical_clinic_be.repository.ClassRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class ClassMutationResolver implements GraphQLMutationResolver {

    private final ClassRepository classRepository;

    public ClassMutationResolver(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Transactional
    @PreAuthorize("hasAuthority('CLASS_ADD_PRIVILEGE')")
    public OperationStatus addClass(ClassInput classInput) {
        return saveClass(null, classInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('CLASS_MODIFY_PRIVILEGE')")
    public OperationStatus updateClass(@NotNull Integer classInputId, ClassInput classInput) {
        return saveClass(classInputId, classInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('CLASS_DELETE_PRIVILEGE')")
    public OperationStatus deleteClass(@NotNull Integer classId) {
        Class userClass = classRepository.findById(classId).orElseThrow(EntityNotFoundException::new);

        OperationStatus opStatus = new OperationStatus(Class.class.getSimpleName(), DictOperationName.DELETE.getCode());
        opStatus.setRecordId(classId);

        classRepository.delete(userClass);

        return opStatus.setSuccess(!classRepository.existsById(classId));
    }

    protected OperationStatus saveClass(Integer classId, ClassInput classInput) {
        boolean adding = (classId == null);
        Class userClass = (adding ? new Class() : classRepository.findById(classId).orElseThrow(EntityNotFoundException::new));

        OperationStatus opStatus = new OperationStatus(Class.class.getSimpleName(), adding ? DictOperationName.ADD.getCode() : DictOperationName.UPDATE.getCode());

        SmartMapper.transferData(classInput, userClass);

        Class classSaved = classRepository.save(userClass);
        Integer id = classSaved.getClassId();
        opStatus.setRecordId(id).setSuccess(id != null);
        return opStatus;
    }
}
