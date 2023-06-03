package com.mb_medical_clinic_be.security.authentication;

import com.mb_medical_clinic_be.dto.Privilege;
import com.mb_medical_clinic_be.entity.User;
import com.mb_medical_clinic_be.entity.Class;
import com.mb_medical_clinic_be.repository.ClassRepository;
import com.mb_medical_clinic_be.repository.PermissionRepository;
import com.mb_medical_clinic_be.security.domain.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentUserFacadeImpl implements CurrentUserFacade {

    private final ClassRepository classRepository;
    private final PermissionRepository permissionRepository;

    public CurrentUserFacadeImpl(ClassRepository classRepository, PermissionRepository permissionRepository) {
        this.classRepository = classRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public CurrentUser convertToCurrentUser(User user) {
        final CurrentUser currentUser = new CurrentUser();

        currentUser.setId(user.getUserId());
        currentUser.setFirstName(user.getFirstname());
        currentUser.setLastName(user.getLastname());
        currentUser.setEmail(user.getEmail());
        currentUser.setLogin(user.getLogin());
        currentUser.setPassword(user.getPassword());
        currentUser.setEnabled(user.getActive() != null ?
                user.getActive() :
                Boolean.FALSE);
        currentUser.setLocked(user.getBlocked() ? user.getBlocked() : Boolean.FALSE);
        currentUser.setAccountExpired(user.getExpireAccountDate());
        currentUser.setPasswordExpired(user.getExpirePasswordDate());
        currentUser.setLastLoginTime(user.getLastLoginTime());
        currentUser.setUserType(user.getUserType());

        List<Class> classes = this.classRepository.findByClassUserSet_UserId(user.getUserId());

        currentUser.addGrantedAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        classes.stream()
                .filter(userClass -> userClass.getAdminClass() != null && userClass.getAdminClass() || StringUtils.equals("0", userClass.getClassCode()))
                .map(lClass -> String.join("_", "ROLE", "ADMIN"))
                .map(SimpleGrantedAuthority::new)
                .forEach(currentUser::addGrantedAuthority);

        List<Privilege> privileges = currentUser.isAdmin() ?
                this.permissionRepository.findAllAdminPrivileges() :
                this.permissionRepository.findByPermissionClass_ClassUserSet_UserId(user.getUserId())
                        .stream()
                        .map(permission -> new Privilege(
                                permission.getCategory().getCode(),
                                permission.getAllowRead(),
                                permission.getAllowAdd(),
                                permission.getAllowModify(),
                                permission.getAllowDelete()))
                        .collect(Collectors.toList());
        mapPrivilegesToCurrentUser(currentUser, privileges);

        return  currentUser;
    }

    private void mapPrivilegesToCurrentUser(CurrentUser currentUser, List<Privilege> privileges) {

        privileges.stream()
                .flatMap(privilege -> {
                    List<String> authorities = new ArrayList<>();
                    if (currentUser.isAdmin()) {
                        authorities.add(String.join("_", privilege.getName().toUpperCase(), "ACCESS", "PRIVILEGE"));
                        authorities.add(String.join( "_", privilege.getName().toUpperCase(), "ADD",    "PRIVILEGE"));
                        authorities.add(String.join("_", privilege.getName().toUpperCase(), "READ",   "PRIVILEGE"));
                        authorities.add(String.join("_", privilege.getName().toUpperCase(), "MODIFY", "PRIVILEGE"));
                        authorities.add(String.join("_", privilege.getName().toUpperCase(), "DELETE", "PRIVILEGE"));

                    } else {
                        if (privilege.getAllowAdd() != null && privilege.getAllowAdd()) {
                            authorities.add(String.join("_", privilege.getName().toUpperCase(), "ADD", "PRIVILEGE"));
                        }

                        if (privilege.getAllowRead() != null && privilege.getAllowRead()) {
                            authorities.add(String.join("_", privilege.getName().toUpperCase(), "READ", "PRIVILEGE"));
                        }

                        if (privilege.getAllowModify() != null && privilege.getAllowModify()) {
                            authorities.add(String.join("_", privilege.getName().toUpperCase(), "MODIFY", "PRIVILEGE"));
                        }

                        if (privilege.getAllowDelete() != null && privilege.getAllowDelete()) {
                            authorities.add(String.join("_", privilege.getName().toUpperCase(), "DELETE", "PRIVILEGE"));
                        }
                    }

                    return authorities.stream();
                }).map(SimpleGrantedAuthority::new)
                .forEach(currentUser::addGrantedAuthority);
    }
}
