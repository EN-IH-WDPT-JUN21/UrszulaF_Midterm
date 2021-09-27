package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.account.CreditCardAccount;
import com.ironhack.midterm.dao.account.SavingAccount;

import java.util.List;

public interface ICreditCardAccountController {
    List<CreditCardAccount> getCreditCardAccounts();
    CreditCardAccount store(CreditCardAccount creditCardAccount);
}
