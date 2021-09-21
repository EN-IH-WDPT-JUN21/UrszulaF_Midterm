package com.ironhack.midterm.dao.account;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class StudentCheckingAccount extends Account {

    public StudentCheckingAccount(Money balance, String secretKey, AccountHolder primaryOwner, List<AccountHolder> secondaryOwners) {
        super(balance, secretKey, primaryOwner, secondaryOwners);
        setPrimaryOwner(primaryOwner);
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        super.setPrimaryOwner(primaryOwner);
        if (this.primaryOwner.age() >= 24) {
            throw new NullPointerException("The primary owner is 24 or older. Create Checking Account");

        }

    }
}
