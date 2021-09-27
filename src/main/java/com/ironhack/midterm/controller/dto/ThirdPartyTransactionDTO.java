package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.user.ThirdParty;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyTransactionDTO {
    private Long id;

    @NotEmpty(message = "Transaction type can't be empty or null.")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Positive(message = "Amount can't be negative or zero.")
    private Money amount;

    @CreationTimestamp
    private LocalDateTime timeStamp;

    private ThirdParty thirdParty;

    @Digits(integer = 26, fraction = 0, message = "Maximum number of digits for accountId is 26, no decimals.")
    private Account recipientAccount;

    public Long getThirdPartyId() {
        return thirdParty.getId();
    }

    public Long getRecipientAccountId() {
        return recipientAccount.getId();
    }
}
