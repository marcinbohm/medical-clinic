package com.mb_medical_clinic_be.resource.user;

import com.mb_medical_clinic_be.entity.User;
import com.mb_medical_clinic_be.repository.UserRepository;
import com.mb_medical_clinic_be.resource.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserById(Integer userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Optional<User> findUserByLogin(String username) {
        return userRepository.findByLogin(username);
    }

    @Override
    public Optional<User> findUserByIdAndRefreshTicket(Integer userId, String refreshToken) {
        return userRepository.findByUserIdAndUserSessionsList_RefreshTicket(userId, refreshToken);
    }
}
