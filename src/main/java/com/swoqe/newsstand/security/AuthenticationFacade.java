package com.swoqe.newsstand.security;

import com.swoqe.newsstand.entities.UserEntity;
import com.swoqe.newsstand.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade implements IAuthenticationFacade {

    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public Optional<UserEntity> getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String name = authentication.getName();
            return userJpaRepository.findBySecurityUserEmail(name);
        }
        return Optional.empty();
    }
}