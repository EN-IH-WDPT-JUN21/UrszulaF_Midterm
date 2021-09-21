package com.ironhack.midterm.dao.account;

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

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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

    private Money creditLimit;

    @DecimalMin(value = "0.1")
    @DecimalMax(value="0.2")
    private BigDecimal interestRate;

    private LocalDateTime lastInterestApplied;

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money creditLimit, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.creditLimit = new Money(Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
        setInterestRate(interestRate);
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money creditLimit) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        setCreditLimit(creditLimit);
        this.interestRate = Constants.CCARD_ACC_DEFAULT_INTEREST_RATE;
    }

    public CreditCardAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.creditLimit = new Money(Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
        this.interestRate = Constants.CCARD_ACC_DEFAULT_INTEREST_RATE;
    }


    public void setCreditLimit(Money creditLimit) {
        if(creditLimit.getAmount().compareTo(BigDecimal.ZERO)<0){
            System.out.println("Credit limit set to default " + Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
            this.creditLimit = new Money(Constants.CCARD_ACC_DEFAULT_CREDIT_LIMIT);
        } else if(creditLimit.getAmount().compareTo(Constants.SAVINGS_ACC_MIN_MIN_BALANCE)>0){
            System.out.println("Credit limit can't be higher than " + Constants.CCARD_ACC_MAX_CREDIT_LIMIT);
            System.out.println("Credit limit set to " + Constants.CCARD_ACC_MAX_CREDIT_LIMIT);
            this.creditLimit = new Money(Constants.CCARD_ACC_MAX_CREDIT_LIMIT);
        }else{
            this.creditLimit = creditLimit;
        }

    }

    public void setInterestRate(BigDecimal interestRate) {
        if(interestRate.compareTo(BigDecimal.ZERO)<0){
            System.out.println("Interest rate set to default " + Constants.CCARD_ACC_DEFAULT_INTEREST_RATE);
            this.interestRate = Constants.CCARD_ACC_DEFAULT_INTEREST_RATE;
        } else if(interestRate.compareTo(Constants.CCARD_ACC_MIN_INTEREST_RATE)<0){
            System.out.println("Interest rate can't be lower than " + Constants.CCARD_ACC_MIN_INTEREST_RATE);
            System.out.println("Interest rate set to " + Constants.CCARD_ACC_MIN_INTEREST_RATE);
            this.interestRate = Constants.CCARD_ACC_MIN_INTEREST_RATE;
        }else{
            this.interestRate = interestRate;
        }


    }
}
