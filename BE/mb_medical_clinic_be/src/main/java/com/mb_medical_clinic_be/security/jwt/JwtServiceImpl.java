package com.mb_medical_clinic_be.security.jwt;

import com.mb_medical_clinic_be.repository.UserRepository;
import com.mb_medical_clinic_be.resource.session.SessionService;
import com.mb_medical_clinic_be.resource.user.UserService;
import com.mb_medical_clinic_be.security.authentication.CurrentUserFacade;
import com.mb_medical_clinic_be.security.domain.CurrentUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class JwtServiceImpl implements JwtService {
    private static final String secret = "M+hG2xDA6N6QGPhT/8tx0uSbX8HFSkIDi4Vn4Q==g/UnRI1b5B11SCKb5WEkPvbO";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final CurrentUserFacade currentUserFacade;
    private final UserService userService;

    public JwtServiceImpl(SessionService sessionService, CurrentUserFacade currentUserFacade, UserService userService) {
        this.sessionService = sessionService;
        this.currentUserFacade = currentUserFacade;
        this.userService = userService;
    }

    public TokenSubject parseToken(String authToken) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(authToken)
                    .getBody();

            Integer id = Integer.parseInt(body.getSubject());
            BearerType bearerType  = BearerType.findByValue(body.get("bt", String.class));

          //  tokenSubject.setUserName(body.getSubject());
          //  tokenSubject.setPersonId(Integer.parseInt((String) body.get("personId")));

            return new TokenSubject(bearerType, id);

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String generateToken(TokenSubject tokenSubject, Duration expirationInMinutes) {
        if (tokenSubject == null
                && tokenSubject.getBearerType() == null) {
            throw new IllegalArgumentException("TokenSubject is empty or incomplete");
        }
        Claims claims = Jwts.claims().setSubject(tokenSubject.getUserId().toString());
        claims.put("bt", tokenSubject.getBearerType().getType());
       // claims.put("personId", tokenSubject.getPersonId());
       // claims.put("userId", tokenSubject.getUserId());

        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiration = issuedAt.plusMinutes(expirationInMinutes.toMinutes());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setIssuedAt(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
    }

    @Override
    public Optional<CurrentUser> loadUserByToken(String token) {
        TokenSubject tokenSubject = null;
        try {
            tokenSubject = this.parseToken(token);
        } catch (Exception e) {
            this.logger.error(e.getMessage());

        }

        if (tokenSubject != null
                && Objects.equals(BearerType.CURRENT_USER, tokenSubject.getBearerType())) {

            return this.sessionService.findByToken(token).map(retToken -> {
                if (retToken.getUserId() != null && userService.findUserById(retToken.getUserId()).isPresent())
                    return this.currentUserFacade.convertToCurrentUser(userService.findUserById(retToken.getUserId()).get());

                return null;
            });
        }

        return Optional.empty();
    }
}
