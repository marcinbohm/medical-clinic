package com.mb_medical_clinic_be.graphql.userclass;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mb_medical_clinic_be.entity.Class;
import com.mb_medical_clinic_be.repository.ClassRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class ClassQueryResolver implements GraphQLQueryResolver {

    private final ClassRepository classRepository;

    public ClassQueryResolver(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('CLASS_READ_PRIVILEGE')")
    public Class getClass(@NotNull Integer classId) {
        return classRepository.findById(classId).orElseThrow(EntityNotFoundException::new);
    }
}
