package com.swoqe.newsstand.service;

import com.swoqe.newsstand.controller.dto.PublisherCreationRequest;
import com.swoqe.newsstand.entities.PublisherEntity;
import com.swoqe.newsstand.repository.PublisherJpaRepository;
import com.swoqe.newsstand.repository.UserJpaRepository;
import com.swoqe.newsstand.security.IAuthenticationFacade;
import com.swoqe.newsstand.security.UserRole;
import com.swoqe.newsstand.utils.PageLink;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class DefaultPublisherService extends AbstractJpaService<PublisherEntity, PublisherJpaRepository>
        implements PublisherService {

    private final IAuthenticationFacade authenticationFacade;
    private final UserJpaRepository userJpaRepository;

    public DefaultPublisherService(PublisherJpaRepository repository, IAuthenticationFacade authenticationFacade, UserJpaRepository userJpaRepository) {
        super(repository);
        this.authenticationFacade = authenticationFacade;
        this.userJpaRepository = userJpaRepository;
    }

    public Page<PublisherEntity> getPublishersByPage(PageLink pageLink) {
        return authenticationFacade.getAuthenticationUser().map(user ->
                repository.findByTitle(
                        user,
                        Objects.toString(pageLink.getTextSearch(), ""),
                        pageLink.toPageable()
                )).orElseGet(() -> repository.findByTitle(
                        Objects.toString(pageLink.getTextSearch(), ""),
                        pageLink.toPageable()
                ));
    }

    @Override
    public void saveByRequest(PublisherCreationRequest publisher) {
        authenticationFacade.getAuthenticationUser().ifPresent(user -> {
            PublisherEntity entity = new PublisherEntity();
            entity.setOfficialName(publisher.getOfficialName());
            entity.setAccount(BigDecimal.ZERO);
            entity.setContactEmail(publisher.getContactEmail());
            entity.setContactPhone(publisher.getContactPhone());
            entity.setDescription(publisher.getDescription());
            entity.setImageUrl(publisher.getImageUrl());
            entity.setPrice(publisher.getPrice());
            entity.setPeriodInDays(publisher.getPeriodInDays());
            entity.setUser(user);
            save(entity);

            user.getSecurityUser().setUserRole(UserRole.PUBLISHER_USER);
            userJpaRepository.save(user);
        });
    }
}
