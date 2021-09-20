package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.Account;

import java.util.List;

public interface IAccountController {
    List<Account> getAccounts();
}
