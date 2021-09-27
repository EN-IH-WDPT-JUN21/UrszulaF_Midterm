package com.ironhack.midterm.dao.account;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.service.impl.Freezable;
import com.ironhack.midterm.service.impl.Penalizable;
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
public class SavingAccount extends Account implements Freezable, Penalizable {
//    @DecimalMin(value = "100")
//    @DecimalMax(value="1000")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="minimum_balance_amount")),
            @AttributeOverride(name="currency",column=@Column(name="minimum_balance_currency"))
    })
    private Money minimumBalance;

    @DecimalMax(value="0.50")
    @Digits(integer=1, fraction=4)
    @Column(columnDefinition = "decimal(5,4) default 0.0025")
    private BigDecimal interestRate;

    @UpdateTimestamp
    private LocalDateTime lastInterestApplied;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="penalty_fee_amount")),
            @AttributeOverride(name="currency",column=@Column(name="penalty_fee_currency"))
    })
    private Money penaltyFee;

    public SavingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money minimumBalance, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public SavingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        this.minimumBalance = new Money(Constants.SAVINGS_ACC_DEFAULT_MIN_BALANCE);
        setInterestRate(interestRate);
    }

    public SavingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        this.minimumBalance = new Money(Constants.SAVINGS_ACC_DEFAULT_MIN_BALANCE);
        this.interestRate = Constants.SAVINGS_ACC_DEFAULT_INTEREST_RATE;
        this.lastInterestApplied = LocalDateTime.now();
    }

    public SavingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money minimumBalance) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        setMinimumBalance(minimumBalance);
        this.interestRate = Constants.SAVINGS_ACC_DEFAULT_INTEREST_RATE;
        this.lastInterestApplied = LocalDateTime.now();
    }


    public void setMinimumBalance(Money minimumBalance) {
        if(minimumBalance.getAmount().compareTo(BigDecimal.ZERO)<0){
            System.out.println("Minimum balance set to default " + Constants.SAVINGS_ACC_DEFAULT_MIN_BALANCE);
            this.minimumBalance = new Money(Constants.SAVINGS_ACC_DEFAULT_MIN_BALANCE);
        } else if(minimumBalance.getAmount().compareTo(Constants.SAVINGS_ACC_MIN_MIN_BALANCE)<0){
            System.out.println("Minimum balance can't be lower than " + Constants.SAVINGS_ACC_MIN_MIN_BALANCE);
            System.out.println("Minimum balance set to " + Constants.SAVINGS_ACC_MIN_MIN_BALANCE);
            this.minimumBalance = new Money(Constants.SAVINGS_ACC_MIN_MIN_BALANCE);
        }else{
            this.minimumBalance = minimumBalance;
        }

    }

    public void setInterestRate(BigDecimal interestRate) {
        if(interestRate.compareTo(BigDecimal.ZERO)<0){
            System.out.println("Interest rate set to default " + Constants.SAVINGS_ACC_DEFAULT_INTEREST_RATE);
            this.interestRate = Constants.SAVINGS_ACC_DEFAULT_INTEREST_RATE;
            this.lastInterestApplied = LocalDateTime.now();
        } else if(interestRate.compareTo(Constants.SAVINGS_ACC_MAX_INTEREST_RATE)>0){
            System.out.println("Interest rate can't be higher than " + Constants.SAVINGS_ACC_MAX_INTEREST_RATE);
            System.out.println("Interest rate set to max " + Constants.SAVINGS_ACC_MAX_INTEREST_RATE);
            this.interestRate = Constants.SAVINGS_ACC_MAX_INTEREST_RATE;
            this.lastInterestApplied = LocalDateTime.now();
        }else{
            this.interestRate = interestRate;
            this.lastInterestApplied = LocalDateTime.now();
        }


    }


}
