package com.swoqe.newsstand.security;

import com.swoqe.newsstand.entities.UserEntity;

import java.util.Optional;

public interface IAuthenticationFacade {
    Optional<UserEntity> getAuthenticationUser();
}