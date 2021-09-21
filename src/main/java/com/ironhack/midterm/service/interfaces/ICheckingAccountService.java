package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.account.CheckingAccount;

public interface ICheckingAccountService {
    void update(Long id, CheckingAccount checkingAccount);
}
