package com.ironhack.midterm.dao;

import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SavingAccount extends Account{
    @DecimalMin(value = "100")
    @DecimalMax(value="1000")
    private Money minimumBalance;

    @DecimalMax(value="0.50")
    private BigDecimal interestRate;

    public SavingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money penaltyFee, Status status, Money minimumBalance, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwners, penaltyFee, status);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }
}
