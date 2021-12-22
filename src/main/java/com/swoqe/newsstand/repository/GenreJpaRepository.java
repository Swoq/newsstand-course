package com.swoqe.newsstand.repository;

import com.swoqe.newsstand.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenreJpaRepository extends JpaRepository<GenreEntity, UUID> {

}
