package com.mb_medical_clinic_be.service;

import com.mb_medical_clinic_be.entity.Session;
import com.mb_medical_clinic_be.resource.session.SessionService;
import com.mb_medical_clinic_be.resource.user.UserService;
import com.mb_medical_clinic_be.security.authentication.ProviderManagerImpl;
import com.mb_medical_clinic_be.security.domain.AuthSession;
import com.mb_medical_clinic_be.security.domain.CurrentUser;
import com.mb_medical_clinic_be.security.domain.LoginType;
import com.mb_medical_clinic_be.security.domain.RefreshToken;
import com.mb_medical_clinic_be.security.jwt.BearerType;
import com.mb_medical_clinic_be.security.jwt.JwtService;
import com.mb_medical_clinic_be.security.jwt.TokenSubject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service("authUserService")
public class AuthUserServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationFacade authenticationFacade;
    private final SessionService sessionService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthUserServiceImpl(AuthenticationManager authenticationManager,
                               AuthenticationFacade authenticationFacade,
                               SessionService sessionService,
                               JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.authenticationFacade = authenticationFacade;
        this.sessionService = sessionService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public AuthSession login(String userName, String password) {
        Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            ProviderManagerImpl providerManager = (ProviderManagerImpl) this.authenticationManager;
            providerManager.setLoginType(LoginType.USER);

            final Authentication authentication = providerManager.authenticate(usernamePasswordAuthentication);
            final CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return generateAuthSession(currentUser.getId());

        } catch (AuthenticationException exception) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw exception;
        }
    }

    @Transactional
    @Override
    public AuthSession refreshToken(RefreshToken refreshToken) {

        return Optional.ofNullable(jwtService.parseToken(refreshToken.getRefreshToken()))
                .filter(tokenSubject -> Objects.equals(BearerType.REFRESH_CURRENT_USER_TOKEN, tokenSubject.getBearerType()))
                .flatMap(tokenSubject -> userService.findUserByIdAndRefreshTicket(tokenSubject.getUserId(), refreshToken.getRefreshToken()))
                .map(user -> this.generateAuthSession(user.getUserId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    @Transactional
    @Override
    public void logout() {
        if (this.authenticationFacade.isCurrentUserAuthenticated()) {
            CurrentUser currentUser = this.authenticationFacade.getCurrentUser();
            this.sessionService.findByUserId(currentUser.getId())
                    .ifPresent(this.sessionService::delete);

            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    protected AuthSession generateAuthSession(Integer userId) {

        AuthSession authSession = new AuthSession();

        Session session = sessionService.findByUserId(userId)
                .orElseGet(() -> createNewSession(userId));

        TokenSubject tokenSubject = new TokenSubject(BearerType.CURRENT_USER, userId);
        Duration durationToken = Duration.ofMinutes(5);
        String token = this.jwtService.generateToken(tokenSubject, durationToken);

        TokenSubject refreshTokenSubject = new TokenSubject(BearerType.REFRESH_CURRENT_USER_TOKEN, userId);
        Duration durationRefreshToken = Duration.ofMinutes(30);
        String refreshToken = this.jwtService.generateToken(refreshTokenSubject, durationRefreshToken);

        session.setTicket(token);
        session.setRefreshTicket(refreshToken);
        session.setLastActive(LocalDateTime.now());

        sessionService.save(session);

        authSession.setAccessToken(session.getTicket());
        authSession.setExpiresIn((int) durationToken.getSeconds());
        authSession.setRefreshToken(session.getRefreshTicket());
        authSession.setRefreshExpiresIn((int) durationRefreshToken.getSeconds());

        return authSession;
    }

    protected Session createNewSession(Integer lUserId) {
        Session newSession = new Session();
        userService.findUserById(lUserId).ifPresent(lUser -> newSession.setUserId(lUser.getUserId()));

        return newSession;
    }

}
