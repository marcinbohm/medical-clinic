package com.mb_medical_clinic_be.graphql.permission;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mb_medical_clinic_be.entity.Permission;
import com.mb_medical_clinic_be.repository.PermissionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class PermissionQueryResolver implements GraphQLQueryResolver {

    private final PermissionRepository permissionRepository;

    public PermissionQueryResolver(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('PERMISSION_READ_PRIVILEGE')")
    public Permission getPermission(@NotNull Integer permissionId) {
        return permissionRepository.findById(permissionId).orElseThrow(EntityNotFoundException::new);
    }
}
