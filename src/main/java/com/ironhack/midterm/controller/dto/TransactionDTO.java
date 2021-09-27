package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.dao.account.Account;
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
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Positive(message = "Amount can't be negative or zero.")
    private Money amount;

    @CreationTimestamp
    private LocalDateTime timeStamp;

    @Digits(integer = 26, fraction = 0, message = "Maximum number of digits for accountId is 26, no decimals.")
    private Account senderAccount;

    @Digits(integer = 26, fraction = 0, message = "Maximum number of digits for accountId is 26, no decimals.")
    private Account recipientAccount;

    public Long getSenderAccountId() {
        return senderAccount.getId();
    }

    public Long getRecipientAccountId() {
        return recipientAccount.getId();
    }

}
