package com.swoqe.newsstand.repository;

import com.swoqe.newsstand.entities.PublisherEntity;
import com.swoqe.newsstand.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublisherJpaRepository extends JpaRepository<PublisherEntity, UUID> {

    @Query("SELECT b FROM PublisherEntity b " +
            "WHERE b.user <> :user AND LOWER(b.officialName) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<PublisherEntity> findByTitle(@Param("user") UserEntity user,
                                      @Param("textSearch") String textSearch,
                                      Pageable pageable);

    @Query("SELECT b FROM PublisherEntity b " +
            "WHERE LOWER(b.officialName) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<PublisherEntity> findByTitle(@Param("textSearch") String textSearch,
                                      Pageable pageable);

    Optional<PublisherEntity> findByUser(UserEntity user);
}
