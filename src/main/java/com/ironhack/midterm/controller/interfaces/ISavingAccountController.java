package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.controller.dto.SavingAccountDTO;
import com.ironhack.midterm.dao.account.SavingAccount;

import java.util.List;

public interface ISavingAccountController {
    List<SavingAccount> getSavingAccounts();
    SavingAccount store(SavingAccount savingAccount);
}
