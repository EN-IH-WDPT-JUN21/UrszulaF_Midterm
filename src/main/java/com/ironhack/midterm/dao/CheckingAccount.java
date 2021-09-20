package com.ironhack.midterm.dao;

import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CheckingAccount extends Account{


    private Money minimumBalance;

    private Money monthlyMaintenanceFee;

    public CheckingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners, Money penaltyFee, Status status, Money minimumBalance, Money monthlyMaintenanceFee) {
        super(balance, secretKey, primaryOwner, secondaryOwners, penaltyFee, status);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        setPrimaryOwner(primaryOwner);
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        super.setPrimaryOwner(primaryOwner);
        if (this.primaryOwner.age() < 24) {
            throw new NullPointerException("The primary owner age is less than 24. Create Student Checking Account");

        }
    }


}
