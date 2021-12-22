package com.swoqe.newsstand.service;

import com.swoqe.newsstand.controller.dto.NewsCreationRequest;
import com.swoqe.newsstand.entities.GenreEntity;
import com.swoqe.newsstand.entities.PublicationEntity;
import com.swoqe.newsstand.entities.PublisherEntity;
import com.swoqe.newsstand.entities.UserEntity;
import com.swoqe.newsstand.repository.GenreJpaRepository;
import com.swoqe.newsstand.repository.PublicationJpaRepository;
import com.swoqe.newsstand.repository.PublisherJpaRepository;
import com.swoqe.newsstand.security.IAuthenticationFacade;
import com.swoqe.newsstand.utils.PageLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@PreAuthorize("hasAnyAuthority('user:write')")
public class DefaultPublicationsService extends AbstractJpaService<PublicationEntity, PublicationJpaRepository>
        implements PublicationService {

    private final IAuthenticationFacade authenticationFacade;
    private final PublisherJpaRepository publisherJpaRepository;
    private final GenreJpaRepository genreJpaRepository;

    public DefaultPublicationsService(PublicationJpaRepository repository,
                                      IAuthenticationFacade authenticationFacade,
                                      PublisherJpaRepository publisherJpaRepository,
                                      GenreJpaRepository genreJpaRepository) {
        super(repository);
        this.authenticationFacade = authenticationFacade;
        this.publisherJpaRepository = publisherJpaRepository;
        this.genreJpaRepository = genreJpaRepository;
    }

    @Transactional
    public Page<PublicationEntity> getPublicationsByPage(PageLink pageLink) {
        Page<PublicationEntity> page = authenticationFacade.getAuthenticationUser()
                .map(user -> repository.findAllBySubscriptions(
                        user,
                        Objects.toString(pageLink.getTextSearch(), ""),
                        pageLink.toPageable()
                )).orElse(new PageImpl<>(Collections.emptyList()));
        return page;
    }

    @Transactional
    public Page<PublicationEntity> getPublicationsByPage(PageLink pageLink, List<UUID> genresIds) {
        Page<PublicationEntity> page = authenticationFacade.getAuthenticationUser()
                .map(user -> repository.findAllBySubscriptions(
                        genresIds,
                        user,
                        Objects.toString(pageLink.getTextSearch(), ""),
                        pageLink.toPageable()
                )).orElse(new PageImpl<>(Collections.emptyList()));
        return page;
    }

    @Override
    public void saveByRequest(NewsCreationRequest request) {
        UserEntity user = authenticationFacade.getAuthenticationUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        PublisherEntity publisher = publisherJpaRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<GenreEntity> genres = genreJpaRepository.findAllById(request.getGenresIds());
        PublicationEntity publication = new PublicationEntity();
        publication.setPublicationDate(LocalDateTime.now());
        publication.setPublisher(publisher);
        publication.setDescription(request.getDescription());
        publication.setGenres(genres);
        publication.setPreview(request.getPreview());
        publication.setTitle(request.getTitle());
        publication.setImageUrl(request.getImageUrl());
        save(publication);
    }
}
