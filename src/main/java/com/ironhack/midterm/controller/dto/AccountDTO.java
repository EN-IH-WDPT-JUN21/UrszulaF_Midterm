package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.dao.account.Transaction;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AccountDTO {


    protected Money balance;

    protected String secretKey;

    @ManyToOne
    @JoinColumn(name = "primaryOwner_id", referencedColumnName = "id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected AccountHolder primaryOwner;

    @ManyToMany
    @JoinTable(
            name="account_secondaryOwners",
            joinColumns = {@JoinColumn(name="account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="secondaryOwner_id", referencedColumnName = "id")}
    )
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected List<AccountHolder> secondaryOwners;

//    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "Date format must be YYYY-MM-DDTHH:MM:SS")
    protected String creationDate;

    protected Status status;

    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactionsSend;

    @OneToMany(mappedBy = "recipientAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactionsReceived;


    public AccountDTO(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwners = secondaryOwners;
        this.status = Status.ACTIVE;
    }


    public AccountDTO(Money balance, String secretKey, AccountHolder primaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.status = Status.ACTIVE;
    }

}
