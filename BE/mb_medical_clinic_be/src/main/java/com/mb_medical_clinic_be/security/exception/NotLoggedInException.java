package com.mb_medical_clinic_be.security.exception;

public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException() {
        super("User is not logged in");
    }
}
