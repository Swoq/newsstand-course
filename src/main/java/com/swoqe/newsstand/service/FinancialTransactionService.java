package com.swoqe.newsstand.service;

import com.swoqe.newsstand.entities.FinancialTransactionEntity;
import com.swoqe.newsstand.entities.PublisherEntity;
import com.swoqe.newsstand.entities.UserEntity;

public interface FinancialTransactionService extends CrudService<FinancialTransactionEntity> {
    FinancialTransactionEntity performTransaction(UserEntity sender, PublisherEntity receiver);
}
