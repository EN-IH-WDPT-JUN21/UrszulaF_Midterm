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

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CheckingAccount extends Account implements Freezable, Penalizable {


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="minimum_balance_amount")),
            @AttributeOverride(name="currency",column=@Column(name="minimum_balance_currency"))
    })
    private Money minimumBalance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="monthly_maintenance_fee_amount")),
            @AttributeOverride(name="currency",column=@Column(name="monthly_maintenance_fee_currency"))
    })
    private Money monthlyMaintenanceFee;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="penalty_fee_amount")),
            @AttributeOverride(name="currency",column=@Column(name="penalty_fee_currency"))
    })
    private Money penaltyFee;

    public CheckingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        this.minimumBalance = new Money(Constants.CHECKING_ACC_MIN_BALANCE);
        this.monthlyMaintenanceFee = new Money(Constants.CHECKING_ACC_DEFAULT_MONTHLY_FEE);
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
