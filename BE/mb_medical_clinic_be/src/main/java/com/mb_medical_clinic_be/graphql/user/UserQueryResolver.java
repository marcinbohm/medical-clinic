package com.mb_medical_clinic_be.graphql.user;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mb_medical_clinic_be.common.SortOrder;
import com.mb_medical_clinic_be.common.SortParamMapper;
import com.mb_medical_clinic_be.dict.DictUserType;
import com.mb_medical_clinic_be.entity.Encounter;
import com.mb_medical_clinic_be.entity.User;
import com.mb_medical_clinic_be.repository.UserRepository;
import com.mb_medical_clinic_be.security.domain.CurrentUser;
import com.mb_medical_clinic_be.service.AuthenticationFacade;
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
public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    public UserQueryResolver(UserRepository userRepository, AuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER_READ_PRIVILEGE')")
    public User getUser(@NotNull Integer userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER_READ_PRIVILEGE')")
    public User getUserByLogin(@NotNull String login) {
        return userRepository.findByLogin(login).orElseThrow(EntityNotFoundException::new);
    }

    public CurrentUser currentUser() {
        return this.authenticationFacade.getCurrentUser();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER_READ_PRIVILEGE')")
    public Page<User> getUserPageByLocationIdAndUserPatient(@NotNull Integer locationId,
                                                                      @NotNull @Min(0) Integer page, @NotNull @Min(0) @Max(100) Integer size, List<SortOrder> orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortParamMapper.map(orderBy)));
        return userRepository.findByLocation_LocationIdAndUserType(locationId, DictUserType.PATIENT.getCode(), pageable);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER_READ_PRIVILEGE')")
    public Page<User> getUserPageByLocationIdAndUserDoctor(@NotNull Integer locationId,
                                                            @NotNull @Min(0) Integer page, @NotNull @Min(0) @Max(100) Integer size, List<SortOrder> orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortParamMapper.map(orderBy)));
        return userRepository.findByLocation_LocationIdAndUserType(locationId, DictUserType.DOCTOR.getCode(), pageable);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER_READ_PRIVILEGE')")
    public List<User> getUsersList() {
        return userRepository.findByUserTypeIsNot(DictUserType.PATIENT.getCode());
    }
}
