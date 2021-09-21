package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.account.SavingAccount;

public interface ISavingAccountService {
    void update(Long id, SavingAccount savingAccount);
}
