package com.mb_medical_clinic_be.controller;

import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.exception.Failure;
import com.mb_medical_clinic_be.resource.user.registration.RegistrationService;
import com.mb_medical_clinic_be.resource.user.registration.model.RegistrationDTO;
import com.mb_medical_clinic_be.security.domain.*;
import com.mb_medical_clinic_be.service.AuthService;
import com.mb_medical_clinic_be.service.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthUserController {

    private final AuthenticationFacade authenticationFacade;
    private final AuthService authUserService;
    private final RegistrationService registrationService;

    public AuthUserController(AuthenticationFacade authenticationFacade,
                              @Qualifier("authUserService") AuthService authUserService, RegistrationService registrationService) {
        this.authenticationFacade = authenticationFacade;
        this.authUserService = authUserService;
        this.registrationService = registrationService;
    }

    @PostMapping(path = "auth/loginUser")
    public AuthSession loginUser(@RequestBody Credentials credentials) {
        return this.authUserService.login(credentials.getUserName(), credentials.getPassword());
    }

    @PostMapping(path = "auth/refreshToken")
    public AuthSession refreshToken(@RequestBody RefreshToken refreshToken) throws Failure {
        return this.authUserService.refreshToken(refreshToken);
    }

    @PostMapping(path = "auth/logoutUser")
    public SuccessPayload logoutUser() {
        this.authUserService.logout();
        return new SuccessPayload("User has been logged out properly");
    }

    @GetMapping(path = "auth/currentUser")
    public CurrentUser getCurrentUser() {
        return authenticationFacade.getCurrentUser();
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<OperationStatus> registration(@RequestBody @Valid RegistrationDTO registrationDTO) {
        return new ResponseEntity<>(registrationService.registerUser(registrationDTO), HttpStatus.CREATED);
    }
}
