package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.ICreditCardAccountController;
import com.ironhack.midterm.dao.account.CheckingAccount;
import com.ironhack.midterm.dao.account.CreditCardAccount;
import com.ironhack.midterm.repository.CreditCardAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CreditCardAccountController implements ICreditCardAccountController {

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @GetMapping("/credit-card-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCardAccount> getCreditCardAccounts(){

        return creditCardAccountRepository.findAll();
    }

    @PostMapping("/credit-card-accounts/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardAccount store(@RequestBody @Valid CreditCardAccount creditCardAccount) {
        return creditCardAccountRepository.save(creditCardAccount);
    }
}
