package com.ironhack.midterm.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    private Money amount;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime timeStamp;

    private Account senderAccount;

    private Account recipientAccount;

    public Long getSenderAccountId() {
        return senderAccount.getId();
    }

    public Long getRecipientAccountId() {
        return recipientAccount.getId();
    }

    public TransactionDTO(TransactionType transactionType, Money amount, Account senderAccount, Account recipientAccount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
    }
}
