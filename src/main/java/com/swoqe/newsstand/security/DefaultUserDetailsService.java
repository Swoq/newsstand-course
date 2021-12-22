package com.swoqe.newsstand.security;

import com.swoqe.newsstand.controller.dto.RegistrationRequest;
import com.swoqe.newsstand.entities.UserEntity;
import com.swoqe.newsstand.repository.UserJpaRepository;
import com.swoqe.newsstand.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService, AuthService {

    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAuthenticationFacade authenticationFacade;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findBySecurityUserEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return user.getSecurityUser();
    }

    @Override
    public void signUpUser(@Valid RegistrationRequest request) {
        userRepository.save(createPlainUserEntity(request));
    }

    @Override
    public String blockUserByEmail(String email) {
        return null;
    }

    @Override
    public String unblockUserByEmail(String email) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('user:read')")
    public Optional<UserEntity> getCurrentUser() {
        return authenticationFacade.getAuthenticationUser();
    }

    private UserEntity createPlainUserEntity(RegistrationRequest request) {
        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setId(UUID.randomUUID());
        securityUserDetails.setPassword(passwordEncoder.encode(request.getPassword()));
        securityUserDetails.setUserRole(UserRole.PLAIN_USER);
        securityUserDetails.setEmail(request.getEmail());
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setSecurityUser(securityUserDetails);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAccount(BigDecimal.ZERO);
        return user;
    }

    public void replenishAccount(BigDecimal amount) {
        authenticationFacade.getAuthenticationUser().ifPresent(user -> {
            user.setAccount(user.getAccount().add(amount));
            userRepository.save(user);
        });
    }


}
