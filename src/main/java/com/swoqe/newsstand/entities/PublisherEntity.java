package com.swoqe.newsstand.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "publisher")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PublisherEntity extends BaseSqlEntity {
    private String officialName;

    @Column(columnDefinition = "text")
    private String description;

    private String imageUrl;

    private String contactEmail;
    private String contactPhone;
    private BigDecimal account;
    private BigDecimal price;
    private Long periodInDays;

    @OneToMany(mappedBy = "publisher")
    @ToString.Exclude
    private List<PublicationEntity> publications;

    @OneToMany(mappedBy = "publisher")
    @ToString.Exclude
    private List<SubscriptionEntity> subscriptions;

    @OneToOne
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PublisherEntity that = (PublisherEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
