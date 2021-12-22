package com.swoqe.newsstand.repository;

import com.swoqe.newsstand.entities.PublicationEntity;
import com.swoqe.newsstand.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PublicationJpaRepository extends JpaRepository<PublicationEntity, UUID> {

    @Query("SELECT b FROM PublicationEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<PublicationEntity> findByTitle(@Param("textSearch") String textSearch, Pageable pageable);

    @Query("SELECT DISTINCT b FROM PublicationEntity b " +
            "JOIN b.genres a " +
            "WHERE a.id IN :genres " +
            "AND (b.title) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<PublicationEntity> findByTitleAndGenres(@Param("genres") List<UUID> genres,
                                          @Param("textSearch") String textSearch,
                                          Pageable pageable);

    @Query("select distinct p from PublicationEntity p " +
            "join p.publisher pub " +
            "left join pub.subscriptions sub " +
            "left JOIN p.genres g " +
            "where ((sub.user = :user and sub.active = true) or pub.user = :user) " +
            "and g.id in :genres " +
            "AND (p.title) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<PublicationEntity> findAllBySubscriptions(@Param("genres") List<UUID> genres,
                                                   @Param("user") UserEntity user,
                                                   @Param("textSearch") String textSearch,
                                                   Pageable pageable);

    @Query("select distinct p from PublicationEntity p " +
            "join p.publisher pub " +
            "left join pub.subscriptions sub " +
            "where ((sub.user = :user and sub.active = true) or pub.user = :user) " +
            "AND (p.title) LIKE LOWER(CONCAT('%', :textSearch, '%'))")
    Page<PublicationEntity> findAllBySubscriptions(@Param("user") UserEntity user,
                                                   @Param("textSearch") String textSearch,
                                                   Pageable pageable);
}
