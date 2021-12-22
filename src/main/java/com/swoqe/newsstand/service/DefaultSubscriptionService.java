package com.swoqe.newsstand.service;

import com.swoqe.newsstand.entities.FinancialTransactionEntity;
import com.swoqe.newsstand.entities.PublisherEntity;
import com.swoqe.newsstand.entities.SubscriptionEntity;
import com.swoqe.newsstand.entities.UserEntity;
import com.swoqe.newsstand.repository.SubscriptionJpaRepository;
import com.swoqe.newsstand.repository.UserJpaRepository;
import com.swoqe.newsstand.security.IAuthenticationFacade;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@PreAuthorize("hasAnyAuthority('user:write')")
@Service
public class DefaultSubscriptionService extends AbstractJpaService<SubscriptionEntity, SubscriptionJpaRepository>
        implements SubscriptionService {

    private final IAuthenticationFacade authenticationFacade;
    private final DefaultFinancialTransactionService transactionService;
    private final PublisherService publisherService;
    private final UserJpaRepository userJpaRepository;

    public DefaultSubscriptionService(SubscriptionJpaRepository repository,
                                      IAuthenticationFacade authenticationFacade,
                                      DefaultFinancialTransactionService transactionService, PublisherService publisherService, UserJpaRepository userJpaRepository) {
        super(repository);
        this.authenticationFacade = authenticationFacade;
        this.transactionService = transactionService;
        this.publisherService = publisherService;
        this.userJpaRepository = userJpaRepository;
    }


    @Override
    @Transactional
    public void performSubscription(UUID publisherId) {
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setId(UUID.randomUUID());
        subscription.setActive(true);

        UserEntity user = authenticationFacade.getAuthenticationUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        PublisherEntity publisher = publisherService.findById(publisherId);

        FinancialTransactionEntity financialTransactionEntity = transactionService.performTransaction(user, publisher);

        subscription.setEndDate(LocalDateTime.now().plusDays(publisher.getPeriodInDays()));
        subscription.setStartDate(LocalDateTime.now());
        subscription.setFinancialTransaction(financialTransactionEntity);
        subscription.setPublisher(publisher);
        subscription.setUser(user);
        repository.save(subscription);
    }

    @Override
    @Transactional
    public void cancelSubscription(UUID subscriptionId) {
        UserEntity user = authenticationFacade.getAuthenticationUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        SubscriptionEntity subscription = repository.findById(subscriptionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!subscription.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        subscription.setActive(false);
        repository.save(subscription);
    }
}
