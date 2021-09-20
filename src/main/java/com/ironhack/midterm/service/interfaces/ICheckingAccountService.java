package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.CheckingAccount;

public interface ICheckingAccountService {
    void update(Long id, CheckingAccount checkingAccount);
}
