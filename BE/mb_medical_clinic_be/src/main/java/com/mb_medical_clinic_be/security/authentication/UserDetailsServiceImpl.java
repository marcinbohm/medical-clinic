package com.mb_medical_clinic_be.security.authentication;

import com.mb_medical_clinic_be.resource.user.UserService;
import com.mb_medical_clinic_be.security.domain.CurrentUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService, MessageSourceAware {

    protected MessageSourceAccessor messages;

    private final UserService userService;
    private final CurrentUserFacade currentUserFacade;

    public UserDetailsServiceImpl(UserService userService,
                                  CurrentUserFacade currentUserFacade) {
        this.userService = userService;
        this.currentUserFacade = currentUserFacade;
    }

    @Override
    public void setMessageSource(@NotNull MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userService.findUserByLogin(username)
                .map(this.currentUserFacade::convertToCurrentUser)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                this.messages.getMessage(
                                        "AbstractUserDetailsAuthenticationProvider.badCredentials",
                                        "Bad Credentials")));
    }
}
