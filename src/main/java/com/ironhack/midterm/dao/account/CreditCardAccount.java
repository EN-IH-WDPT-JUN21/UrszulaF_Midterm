package com.ironhack.midterm.dao.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Constants;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditCardAccount extends Account {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="credit_limit_amount")),
            @AttributeOverride(name="currency",column=@Column(name="credit_limit_currency"))
    })
    private Money creditLimit;

    @DecimalMin(value = "0.1")
    @Digits(integer=3, fraction=4)
    @Column(columnDefinition = "decimal(7,4) default 0.2")
    private BigDecimal interestRate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
//    @UpdateTimestamp
    private LocalDateTime lastInterestApplied;

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money creditLimit, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
        this.lastInterestApplied = LocalDateTime.now();
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.creditLimit = new Money(Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
        setInterestRate(interestRate);
        this.lastInterestApplied = LocalDateTime.now();
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money creditLimit) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        setCreditLimit(creditLimit);
        this.interestRate = Constants.CCARD_ACC_DEFAULT_INTEREST_RATE;
        this.lastInterestApplied = LocalDateTime.now();
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.creditLimit = new Money(Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
        this.interestRate = Constants.CCARD_ACC_DEFAULT_INTEREST_RATE;
        this.lastInterestApplied = LocalDateTime.now();
    }

    //for creating accounts with past date - for testing purposes
    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money creditLimit, BigDecimal interestRate, LocalDateTime creationDate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
        this.lastInterestApplied=creationDate;
//        this.lastInterestApplied = LocalDateTime.now();
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, BigDecimal interestRate, LocalDateTime creationDate) {
        super(balance, secretKey, primaryOwner, secondaryOwners, creationDate);
        this.creditLimit = new Money(Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
        setInterestRate(interestRate);
        this.lastInterestApplied=creationDate;
//        this.lastInterestApplied = LocalDateTime.now();
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money creditLimit, LocalDateTime creationDate) {
        super(balance, secretKey, primaryOwner, secondaryOwners, creationDate);
        setCreditLimit(creditLimit);
        this.interestRate = Constants.CCARD_ACC_DEFAULT_INTEREST_RATE;
        this.lastInterestApplied=creationDate;
//        this.lastInterestApplied = LocalDateTime.now();
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, LocalDateTime creationDate) {
        super(balance, secretKey, primaryOwner, secondaryOwners, creationDate);
        this.creditLimit = new Money(Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
        this.interestRate = Constants.CCARD_ACC_DEFAULT_INTEREST_RATE;
        this.lastInterestApplied=creationDate;
//        this.lastInterestApplied = LocalDateTime.now();
    }


    public void setCreditLimit(Money creditLimit) {
        if(creditLimit.getAmount().compareTo(BigDecimal.ZERO)<0){
            System.out.println("Credit limit set to default " + Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
            this.creditLimit = new Money(Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
        } else if(creditLimit.getAmount().compareTo(Constants.CCARD_ACC_MAX_CREDIT_LIMIT)>0){
            System.out.println("Credit limit can't be higher than " + Constants.CCARD_ACC_MAX_CREDIT_LIMIT);
            System.out.println("Credit limit set to " + Constants.CCARD_ACC_MAX_CREDIT_LIMIT);
            this.creditLimit = new Money(Constants.CCARD_ACC_MAX_CREDIT_LIMIT);
        }else{
            this.creditLimit = creditLimit;
        }

    }

    public void setInterestRate(BigDecimal interestRate) {
        if(interestRate.compareTo(Constants.CCARD_ACC_MIN_INTEREST_RATE)<0){
            System.out.println("Interest rate can't be lower than " + Constants.CCARD_ACC_MIN_INTEREST_RATE);
            System.out.println("Interest rate set to min " + Constants.CCARD_ACC_MIN_INTEREST_RATE);
            this.interestRate = Constants.CCARD_ACC_MIN_INTEREST_RATE;
        }else{
            this.interestRate = interestRate;
        }


    }
}
