package com.mb_medical_clinic_be.entity;

import org.springframework.boot.actuate.audit.listener.AuditListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "session")
@EntityListeners(AuditListener.class)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_generator")
    @SequenceGenerator(name="session_generator", sequenceName = "session_session_id_seq", allocationSize=1)
    @Column(name = "session_id")
    private Integer sessionId;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "ticket")
    private String ticket;

    @NotNull
    @Column(name = "last_active")
    private LocalDateTime lastActive;

    @Column(name = "refresh_ticket")
    private String refreshTicket;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            insertable = false,
            updatable = false
    )
    private User user;

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
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public String getRefreshTicket() {
        return refreshTicket;
    }

    public void setRefreshTicket(String refreshTicket) {
        this.refreshTicket = refreshTicket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
