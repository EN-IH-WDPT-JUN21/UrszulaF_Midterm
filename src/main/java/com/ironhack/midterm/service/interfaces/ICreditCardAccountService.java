package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.account.CreditCardAccount;

public interface ICreditCardAccountService {
    void update(Long id, CreditCardAccount creditCardAccount);
}
