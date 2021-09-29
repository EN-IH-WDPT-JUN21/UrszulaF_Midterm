package com.ironhack.midterm.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
public class ThirdPartyTransactionSendDTO {

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Money amount;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime timeStamp;



    @Digits(integer = 26, fraction = 0, message = "Maximum number of digits for accountId is 26, no decimals.")
    private Long recipientAccountId;

    protected String secretKey;

    public ThirdPartyTransactionSendDTO(TransactionType transactionType, Money amount, Long recipientAccountId, String secretKey) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.recipientAccountId = recipientAccountId;
        this.secretKey = secretKey;
    }
}
