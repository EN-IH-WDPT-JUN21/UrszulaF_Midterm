package com.ironhack.midterm.controller.dto;

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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountDTO extends AccountDTO{

    private Money minimumBalance;
    

    private BigDecimal interestRate;

//    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "Date format must be YYYY-MM-DD HH:MM:SS.000000")
    private String lastInterestApplied;

    private Money penaltyFee;

    public SavingAccountDTO(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money minimumBalance, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public SavingAccountDTO(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        this.minimumBalance = new Money(Constants.SAVINGS_ACC_DEFAULT_MIN_BALANCE);
        setInterestRate(interestRate);
    }

    public SavingAccountDTO(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        this.minimumBalance = new Money(Constants.SAVINGS_ACC_DEFAULT_MIN_BALANCE);
        this.interestRate = Constants.SAVINGS_ACC_DEFAULT_INTEREST_RATE;
//        this.lastInterestApplied = LocalDateTime.now();
    }

    public SavingAccountDTO(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money minimumBalance) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        setMinimumBalance(minimumBalance);
        this.interestRate = Constants.SAVINGS_ACC_DEFAULT_INTEREST_RATE;
//        this.lastInterestApplied = LocalDateTime.now();
    }

    public SavingAccountDTO(Money balance, String secretKey, AccountHolder primaryOwner) {
        super(balance, secretKey, primaryOwner);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        this.minimumBalance = new Money(Constants.SAVINGS_ACC_DEFAULT_MIN_BALANCE);
        this.interestRate = Constants.SAVINGS_ACC_DEFAULT_INTEREST_RATE;
//        this.lastInterestApplied = LocalDateTime.now();
    }

    public SavingAccountDTO(Money balance, String secretKey, AccountHolder primaryOwner, Money minimumBalance) {
        super(balance, secretKey, primaryOwner);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        setMinimumBalance(minimumBalance);
        this.interestRate = Constants.SAVINGS_ACC_DEFAULT_INTEREST_RATE;
//        this.lastInterestApplied = LocalDateTime.now();
    }


    
}
