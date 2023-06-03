package com.mb_medical_clinic_be.resource.user.registration;

import com.mb_medical_clinic_be.VerificationStatus;
import com.mb_medical_clinic_be.entity.User;
import com.mb_medical_clinic_be.exception.VerificationStatusException;
import com.mb_medical_clinic_be.repository.UserRepository;
import com.mb_medical_clinic_be.resource.user.registration.model.RegistrationDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationVerificationServiceImpl implements RegistrationVerificationService {

    private final Log logger = LogFactory.getLog(this.getClass());

    private final UserRepository userRepository;

    public RegistrationVerificationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public VerificationStatus verifyUserRegistration(RegistrationDTO registrationDTO) {

        VerificationStatus status = new VerificationStatus()
                .setAccepted(true);

        try {
            checkIfLoginExistsInDb(registrationDTO.getLogin());
            isStrongPassword(registrationDTO.getPassword());
        } catch (VerificationStatusException e) {
            logger.error("User registration verification not passed!");
            status.setAccepted(false).setMessage(e.getMessage());
        }

        return status;
    }

    public void checkIfLoginExistsInDb(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isPresent())
            throw new VerificationStatusException("User with provided login exists in database!");
    }

    public void isStrongPassword(String password) {

        if (password == null)
            return;

        String atLeastOneDigit = "(?=.*[0-9])";
        String atLeastOneLowerCaseLetter = "(?=.*[a-z])";
        String atLeastOneUpperCaseLetter = "(?=.*[A-Z])";
        String atLeastOneSpecialCharacter = "(?=.*[+=!@#$%^&*(){}\\[\\]\\|:\";'<>?,./])";
        String noWhitespaceAllowed = "(?=\\S+$)";
        String atLeastEightCharacter = ".{8,}";

        String pattern = "^" +
                atLeastOneDigit +
                atLeastOneLowerCaseLetter +
                atLeastOneUpperCaseLetter +
                atLeastOneSpecialCharacter +
                noWhitespaceAllowed +
                atLeastEightCharacter +
                "$";


        if (!password.matches(pattern))
            throw new VerificationStatusException("Has³o nie jest wystarczaj¹co trudne!");
    }
}
