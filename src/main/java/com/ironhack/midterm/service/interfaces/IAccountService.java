package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.Account;

public interface IAccountService {
    void update(Long id, Account account);
}
