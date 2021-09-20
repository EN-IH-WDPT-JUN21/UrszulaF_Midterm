package com.ironhack.midterm.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Money balance;

    protected String secretKey;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "primaryOwner_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
    protected List<AccountHolder> secondaryOwners;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    protected Money penaltyFee;

    protected Date creationDate;

    @Enumerated(EnumType.STRING)
    protected Status status;

    public Account(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money penaltyFee, Status status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwners = secondaryOwners;
        this.penaltyFee = penaltyFee;
        this.creationDate = new Date();
        this.status = status;
    }


}
