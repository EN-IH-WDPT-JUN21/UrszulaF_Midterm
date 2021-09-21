package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.account.CheckingAccount;

import java.util.List;

public interface ICheckingAccountController {
    List<CheckingAccount> getCheckingAccounts();
}
