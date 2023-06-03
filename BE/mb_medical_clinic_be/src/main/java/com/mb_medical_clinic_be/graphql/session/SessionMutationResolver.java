package com.mb_medical_clinic_be.graphql.session;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.dict.DictOperationName;
import com.mb_medical_clinic_be.entity.Session;
import com.mb_medical_clinic_be.mapper.SmartMapper;
import com.mb_medical_clinic_be.repository.SessionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class SessionMutationResolver implements GraphQLMutationResolver {

    private final SessionRepository sessionRepository;

    public SessionMutationResolver(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    @PreAuthorize("hasAuthority('SESSION_ADD_PRIVILEGE')")
    public OperationStatus addSession(SessionInput sessionInput) {
        return saveSession(null, sessionInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SESSION_MODIFY_PRIVILEGE')")
    public OperationStatus updateSession(@NotNull Integer sessionInputId, SessionInput sessionInput) {
        return saveSession(sessionInputId, sessionInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SESSION_DELETE_PRIVILEGE')")
    public OperationStatus deleteSession(@NotNull Integer sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(EntityNotFoundException::new);

        OperationStatus opStatus = new OperationStatus(Session.class.getSimpleName(), DictOperationName.DELETE.getCode());
        opStatus.setRecordId(sessionId);

        sessionRepository.delete(session);

        return opStatus.setSuccess(!sessionRepository.existsById(sessionId));
    }

    protected OperationStatus saveSession(Integer sessionId, SessionInput sessionInput) {
        boolean adding = (sessionId == null);
        Session session = (adding ? new Session() : sessionRepository.findById(sessionId).orElseThrow(EntityNotFoundException::new));

        OperationStatus opStatus = new OperationStatus(Session.class.getSimpleName(), adding ? DictOperationName.ADD.getCode() : DictOperationName.UPDATE.getCode());

        SmartMapper.transferData(sessionInput, session);

        Session sessionSaved = sessionRepository.save(session);
        Integer id = sessionSaved.getSessionId();
        opStatus.setRecordId(id).setSuccess(id != null);
        return opStatus;
    }
}
