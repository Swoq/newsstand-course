package com.swoqe.newsstand.service;

import com.swoqe.newsstand.entities.SubscriptionEntity;

import java.util.UUID;

public interface SubscriptionService extends CrudService<SubscriptionEntity> {
    void performSubscription(UUID id);

    void cancelSubscription(UUID id);
}
