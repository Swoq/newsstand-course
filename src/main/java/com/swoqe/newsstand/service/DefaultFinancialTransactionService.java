package com.swoqe.newsstand.service;

import com.swoqe.newsstand.entities.*;
import com.swoqe.newsstand.repository.TransactionJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class DefaultFinancialTransactionService extends AbstractJpaService<FinancialTransactionEntity, TransactionJpaRepository>
        implements FinancialTransactionService {

    public DefaultFinancialTransactionService(TransactionJpaRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public FinancialTransactionEntity performTransaction(UserEntity sender, PublisherEntity receiver) {
        FinancialTransaction financialTransaction = ImmutableFinancialTransaction.builder()
                .status(FinancialTransactionStatus.AWAITING)
                .timestamp(System.currentTimeMillis())
                .sender(sender)
                .receiver(receiver)
                .build();

        if (sender.getAccount().compareTo(receiver.getPrice()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to perform subscriptions");
        }

        BigDecimal senderAccount = sender.getAccount().subtract(receiver.getPrice());
        sender.setAccount(senderAccount);

        BigDecimal receiverAccount = receiver.getAccount().add(receiver.getPrice());
        receiver.setAccount(receiverAccount);

        return repository.save(new FinancialTransactionEntity(financialTransaction));
    }
}
