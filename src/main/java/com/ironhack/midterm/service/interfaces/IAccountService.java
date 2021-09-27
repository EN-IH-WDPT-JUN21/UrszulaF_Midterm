package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Money;

import java.util.List;

public interface IAccountService {
    void update(Long id, Account account);
    void updateBalance(Long id, Money balance);
    void updateStatus(Long id, Status status);
    List<Account> getMyPrimaryAccounts(Long id);
    List<Account> getMySecondaryAccounts(Long id);
}
