package com.mb_medical_clinic_be.security.authentication;

import com.mb_medical_clinic_be.security.domain.LoginType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

public class ProviderManagerImpl extends ProviderManager {

    private LoginType loginType;
    private DaoAuthenticationProvider daoAdminAuthenticationProvider;
    private DaoAuthenticationProvider daoUserAuthenticationProvider;

    public ProviderManagerImpl(List<AuthenticationProvider> providers) {
        super(providers);
    }

    @Override
    public List<AuthenticationProvider> getProviders() {

        List<AuthenticationProvider> providers = new ArrayList<>();

        switch (loginType) {
            case USER:
                providers.add(daoUserAuthenticationProvider);
                break;
            case ADMIN:
                providers.add(daoAdminAuthenticationProvider);
                break;
            default:
                break;
        }

        return providers;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public void setDaoAdminAuthenticationProvider(DaoAuthenticationProvider daoAdminAuthenticationProvider) {
        this.daoAdminAuthenticationProvider = daoAdminAuthenticationProvider;
    }

    public void setDaoUserAuthenticationProvider(DaoAuthenticationProvider daoUserAuthenticationProvider) {
        this.daoUserAuthenticationProvider = daoUserAuthenticationProvider;
    }
}