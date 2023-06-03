package com.mb_medical_clinic_be;

public class VerificationStatus {
    private boolean accepted;
    private String message;

    public VerificationStatus() {
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public VerificationStatus setAccepted(boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public VerificationStatus setMessage(String message) {
        this.message = message;
        return this;
    }
}
