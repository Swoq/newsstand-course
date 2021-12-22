package com.swoqe.newsstand.service;

import java.util.List;
import java.util.UUID;

public interface CrudService<T> {

    List<T> findAll();

    T findById(UUID id);

    T save(T object);

    void delete(T object);

    void deleteById(UUID id);
}