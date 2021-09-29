package com.ironhack.midterm.dao.account;

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
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ThirdPartyTransactionReceive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Embedded
    private Money amount;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name="senderAccountId", referencedColumnName = "id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name="recipientThirdPartyId", referencedColumnName = "id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ThirdParty recipientThirdParty;


    public ThirdPartyTransactionReceive(TransactionType transactionType, Money amount, Account senderAccount, ThirdParty recipientThirdParty) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.senderAccount = senderAccount;
        this.recipientThirdParty = recipientThirdParty;
        this.timeStamp = LocalDateTime.now();
    }
}
