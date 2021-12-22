package com.swoqe.newsstand.service;

import com.swoqe.newsstand.entities.GenreEntity;
import com.swoqe.newsstand.repository.GenreJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultGenreService extends AbstractJpaService<GenreEntity, GenreJpaRepository> implements GenreService {

    public DefaultGenreService(GenreJpaRepository repository) {
        super(repository);
    }
}
