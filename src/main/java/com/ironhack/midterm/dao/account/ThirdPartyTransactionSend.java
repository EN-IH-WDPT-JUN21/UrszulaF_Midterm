package com.ironhack.midterm.dao.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
public class ThirdPartyTransactionSend {
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
    @JoinColumn(name="senderThirdPartyId", referencedColumnName = "id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ThirdParty senderThirdParty;

    @ManyToOne
    @JoinColumn(name="recipientAccountId", referencedColumnName = "id")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account recipientAccount;


    public ThirdPartyTransactionSend(TransactionType transactionType, Money amount, ThirdParty senderThirdParty, Account recipientAccount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.senderThirdParty = senderThirdParty;
        this.recipientAccount = recipientAccount;
        this.timeStamp = LocalDateTime.now();
    }
}
