package com.ironhack.midterm.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Money amount;

    @ManyToOne
    @JoinColumn(name="account_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;

    @ManyToOne
    @JoinColumn(name="accountSecond_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account accountSecond;

//    one side transaction
    public Transaction(TransactionType transactionType, Money amount, Account account) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.account = account;
    }

//    two-sides transaction
    public Transaction(TransactionType transactionType, Money amount, Account account, Account accountSecond) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.account = account;
        this.accountSecond = accountSecond;
    }
}
