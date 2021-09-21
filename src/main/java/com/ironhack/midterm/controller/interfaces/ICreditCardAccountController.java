package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.account.CreditCardAccount;

import java.util.List;

public interface ICreditCardAccountController {
    List<CreditCardAccount> getCreditCardAccounts();
}
