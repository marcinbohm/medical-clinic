package com.mb_medical_clinic_be.graphql.session;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SessionInput {

    private Integer sessionId;
    private Integer userId;
    private String ticket;
    private LocalDateTime lastActive;
    private String refreshTicket;

    private boolean userIdSetFromInput = false;
    private boolean ticketSetFromInput = false;
    private boolean lastActiveSetFromInput = false;
    private boolean refreshTicketSetFromInput = false;

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        this.userIdSetFromInput = true;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
        this.ticketSetFromInput = true;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
        this.lastActiveSetFromInput = true;
    }

    public String getRefreshTicket() {
        return refreshTicket;
    }

    public void setRefreshTicket(String refreshTicket) {
        this.refreshTicket = refreshTicket;
        this.refreshTicketSetFromInput = true;
    }

    public boolean isUserIdSetFromInput() {
        return this.userIdSetFromInput;
    }

    public boolean isTicketSetFromInput() {
        return this.ticketSetFromInput;
    }

    public boolean isLastActiveSetFromInput() {
        return this.lastActiveSetFromInput;
    }


    public boolean isRefreshTicketSetFromInput() {
        return this.refreshTicketSetFromInput;
    }

}
