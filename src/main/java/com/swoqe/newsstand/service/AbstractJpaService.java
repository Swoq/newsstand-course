package com.swoqe.newsstand.service;

import com.swoqe.newsstand.entities.BaseSqlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

public abstract class AbstractJpaService<T extends BaseSqlEntity, R extends JpaRepository<T, UUID>> {

    protected R repository;

    public AbstractJpaService(R repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(UUID id) {
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (id != null) {
            return repository.findById(id).orElseThrow(() -> responseStatusException);
        } else {
            throw responseStatusException;
        }
    }

    public T save(T object) {
        if (object.getId() == null) {
            object.setId(UUID.randomUUID());
        }
        return repository.save(object);
    }

    @Transactional
    public void delete(T object) {
        if (object == null || object.getId() == null || !repository.existsById(object.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.delete(object);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (id == null || !repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }


}
