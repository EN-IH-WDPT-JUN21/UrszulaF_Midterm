package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.account.Account;

public interface IAccountService {
    void update(Long id, Account account);
}
