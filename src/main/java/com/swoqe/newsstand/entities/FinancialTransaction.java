package com.swoqe.newsstand.entities;

import org.immutables.value.Value;

@Value.Immutable
public interface FinancialTransaction {
    Long timestamp();
    FinancialTransactionStatus status();
    UserEntity sender();
    PublisherEntity receiver();
}
