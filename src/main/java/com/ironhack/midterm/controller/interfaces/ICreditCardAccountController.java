package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.CreditCardAccount;

import java.util.List;

public interface ICreditCardAccountController {
    List<CreditCardAccount> getCreditCardAccounts();
}
