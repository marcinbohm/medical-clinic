package com.mb_medical_clinic_be.graphql.session;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mb_medical_clinic_be.entity.Session;
import com.mb_medical_clinic_be.repository.SessionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class SessionQueryResolver implements GraphQLQueryResolver {

    private final SessionRepository sessionRepository;

    public SessionQueryResolver(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('SESSION_READ_PRIVILEGE')")
    public Session getSession(@NotNull Integer sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('SESSION_READ_PRIVILEGE')")
    public Session getSessionByUserId(@NotNull Integer userId) {
        return sessionRepository.findByUserId(userId).orElseThrow(EntityNotFoundException::new);
    }
}
