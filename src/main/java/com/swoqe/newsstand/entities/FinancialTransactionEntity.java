package com.swoqe.newsstand.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "transactions")
public class FinancialTransactionEntity extends BaseSqlEntity {

    private Long timestamp;

    @Enumerated(EnumType.STRING)
    private FinancialTransactionStatus status;

    @OneToOne
    private UserEntity sender;

    @OneToOne
    private PublisherEntity receiver;

    public FinancialTransactionEntity(FinancialTransaction financialTransaction) {
        super(UUID.randomUUID());
        this.timestamp = financialTransaction.timestamp();
        this.status = financialTransaction.status();
        this.sender = financialTransaction.sender();
        this.receiver = financialTransaction.receiver();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FinancialTransactionEntity that = (FinancialTransactionEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
