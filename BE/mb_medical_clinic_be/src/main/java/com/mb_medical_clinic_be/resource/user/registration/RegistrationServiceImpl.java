package com.mb_medical_clinic_be.resource.user.registration;

import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.VerificationStatus;
import com.mb_medical_clinic_be.dict.DictOperationName;
import com.mb_medical_clinic_be.entity.User;
import com.mb_medical_clinic_be.mapper.SmartMapper;
import com.mb_medical_clinic_be.repository.UserRepository;
import com.mb_medical_clinic_be.resource.user.registration.model.RegistrationDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final RegistrationVerificationService registrationVerificationService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserRepository userRepository,
                                   RegistrationVerificationService registrationVerificationService,
                                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.registrationVerificationService = registrationVerificationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OperationStatus registerUser(RegistrationDTO registrationDTO) {

        OperationStatus operationStatus = new OperationStatus()
                .setOperationName(DictOperationName.ADD.getCode())
                .setTargetClassName(User.class.getSimpleName());

        VerificationStatus verificationStatus = registrationVerificationService
                .verifyUserRegistration(registrationDTO);

        if (!verificationStatus.isAccepted())
            return operationStatus.setSuccess(false)
                    .addMessage(verificationStatus.getMessage());
        else {
            User user = new User();

            user.setPassword(registrationDTO.getPassword());
            user.setLocationId(registrationDTO.getLocationId());
            user.setFirstname(registrationDTO.getFirstname());
            user.setLastname(registrationDTO.getLastname());
            user.setEmail(registrationDTO.getEmail());
            user.setLogin(registrationDTO.getLogin());
            user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
            user.setActive(true);
            user.setBlocked(false);
            user.setCreatedAt(LocalDateTime.now());
            user.setCreatedBy(registrationDTO.getUserId());
            user.setExpireAccountDate(LocalDate.now().plusYears(1));
            user.setExpirePasswordDate(LocalDate.now().plusMonths(1));

            User userSaved = userRepository.save(user);
            Integer id = userSaved.getUserId();
            operationStatus.setRecordId(id).setSuccess(id != null);

            return operationStatus;
        }
    }
}
