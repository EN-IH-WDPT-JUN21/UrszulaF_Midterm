package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Money amount;

    private Account senderAccount;

    private Account recipientAccount;

    public Long getSenderAccountId() {
        return senderAccount.getId();
    }

    public Long getRecipientAccountId() {
        return recipientAccount.getId();
    }

}
