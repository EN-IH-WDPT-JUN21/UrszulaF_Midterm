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

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CheckingAccount extends Account implements Freezable, Penalizable {


    private Money minimumBalance;

    private Money monthlyMaintenanceFee;

    private Money penaltyFee;

    public CheckingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.minimumBalance = new Money(Constants.CHECKING_ACC_DEFAULT_MONTHLY_FEE);
        this.monthlyMaintenanceFee = new Money(Constants.CHECKING_ACC_MIN_BALANCE);
        this.penaltyFee = new Money(BigDecimal.ZERO);
        setPrimaryOwner(primaryOwner);
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        super.setPrimaryOwner(primaryOwner);
        if (this.primaryOwner.age() < 24) {
            throw new NullPointerException("The primary owner age is less than 24. Create Student Checking Account");

        }
    }


}
