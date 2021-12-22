package com.swoqe.newsstand.entities;

import com.swoqe.newsstand.security.SecurityUserDetails;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "application_user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserEntity extends BaseSqlEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private SecurityUserDetails securityUser;

    private String firstName;
    private String lastName;
    private BigDecimal account;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SubscriptionEntity> subscriptions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
