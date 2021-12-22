package com.swoqe.newsstand.repository;

import com.swoqe.newsstand.entities.UserEntity;
import com.swoqe.newsstand.security.SecurityUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findBySecurityUserEmail(String email);
    Optional<UserEntity> findBySecurityUser(SecurityUserDetails securityUserDetails);
}
