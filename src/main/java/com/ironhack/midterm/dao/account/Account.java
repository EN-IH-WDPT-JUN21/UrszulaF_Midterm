package com.ironhack.midterm.dao.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.service.impl.Freezable;
import com.ironhack.midterm.service.impl.Penalizable;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements Freezable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="balance_amount")),
            @AttributeOverride(name="currency",column=@Column(name="balance_currency"))
    })
    protected Money balance;

    protected String secretKey;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "primaryOwner_id", referencedColumnName = "id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected AccountHolder primaryOwner;

//    @ManyToOne
//    @JoinColumn(name = "secondaryOwner_id", referencedColumnName = "id")
//    protected AccountHolder secondaryOwner;

    @ManyToMany
    @JoinTable(
            name="account_secondaryOwners",
            joinColumns = {@JoinColumn(name="account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="secondaryOwner_id", referencedColumnName = "id")}
    )
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected List<AccountHolder> secondaryOwners;

    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> transactionsSend;

    @OneToMany(mappedBy = "recipientAccount", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> transactionsReceived;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
//    @Column(updatable=false)
//    @CreationTimestamp
    protected LocalDateTime creationDate;

    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    protected Status status;

    public Account(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwners = secondaryOwners;
        this.creationDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }


    public Account(Money balance, String secretKey, AccountHolder primaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.creationDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }
//for creating accounts with past date - for testing purposes
    public Account(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, LocalDateTime creationDate) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwners = secondaryOwners;
        this.creationDate = creationDate;
        this.status = Status.ACTIVE;
    }

    public Account(Money balance, String secretKey, AccountHolder primaryOwner, LocalDateTime creationDate) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.creationDate = creationDate;
        this.status = Status.ACTIVE;
    }
}
