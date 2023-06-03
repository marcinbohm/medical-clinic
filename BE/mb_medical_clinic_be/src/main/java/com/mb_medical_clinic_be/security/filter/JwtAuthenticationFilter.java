package com.mb_medical_clinic_be.security.filter;

import com.mb_medical_clinic_be.security.domain.CurrentUser;
import com.mb_medical_clinic_be.security.jwt.JwtService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Transactional
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        Optional<CurrentUser> currUser = this.getJwtFromRequest(request)
                .flatMap(token -> this.jwtService.loadUserByToken(token)
                        .filter(CurrentUser::isEnabled)
                        .filter(CurrentUser::isAccountNonLocked)
                        .map(currentUser -> currentUser.setJwtToken(token))
                        .map(currentUser -> currentUser.setPassword(null))
                );

        if (currUser.isPresent()) {
            CurrentUser currentUser = currUser.get();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }  

        chain.doFilter(request, response);
    }

    private Optional<String> getJwtFromRequest(HttpServletRequest request) {
        return
                Optional.ofNullable(request.getHeader("Authorization"))
                        .filter(StringUtils::isNotBlank)
                        .filter(token -> StringUtils.startsWith(token, "Bearer "))
                        .map(token -> token.substring(7));
    }

}
