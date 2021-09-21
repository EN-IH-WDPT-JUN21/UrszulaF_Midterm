package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.account.CreditCardAccount;
import com.ironhack.midterm.repository.CreditCardAccountRepository;
import com.ironhack.midterm.service.interfaces.ICreditCardAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardAccountService implements ICreditCardAccountService {
    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    public void update(Long id, CreditCardAccount creditCardAccount){
        Optional<CreditCardAccount> storedCreditCardAccount = creditCardAccountRepository.findById(id);
        if(storedCreditCardAccount.isPresent()){
            creditCardAccount.setId(storedCreditCardAccount.get().getId());
            creditCardAccountRepository.save(creditCardAccount);
        }
    }
}
