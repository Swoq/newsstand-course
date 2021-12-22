package com.swoqe.newsstand.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "publication")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PublicationEntity extends BaseSqlEntity {
    private String title;

    @Column(columnDefinition = "text")
    private String preview;

    @Column(columnDefinition = "text")
    private String description;

    private LocalDateTime publicationDate;
    private String imageUrl;

    @ManyToOne()
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisher;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "publication_genre",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @ToString.Exclude
    private List<GenreEntity> genres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PublicationEntity that = (PublicationEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
