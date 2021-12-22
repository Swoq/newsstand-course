package com.swoqe.newsstand.repository;

import com.swoqe.newsstand.entities.SubscriptionEntity;
import com.swoqe.newsstand.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionEntity, UUID> {
}
