package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.account.CheckingAccount;
import com.ironhack.midterm.dao.account.SavingAccount;

import java.util.List;

public interface ICheckingAccountController {
    List<CheckingAccount> getCheckingAccounts();
    CheckingAccount store(CheckingAccount checkingAccount);
}
