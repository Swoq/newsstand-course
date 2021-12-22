package com.swoqe.newsstand.service;

import com.swoqe.newsstand.controller.dto.RegistrationRequest;
import com.swoqe.newsstand.entities.UserEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface AuthService {

    void signUpUser(RegistrationRequest request);

    String blockUserByEmail(String email);

    String unblockUserByEmail(String email);

    Optional<UserEntity> getCurrentUser();

    void replenishAccount(BigDecimal amount);
}
