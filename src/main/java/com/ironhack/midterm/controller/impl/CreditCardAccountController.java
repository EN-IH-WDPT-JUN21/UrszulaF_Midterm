package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.ICreditCardAccountController;
import com.ironhack.midterm.dao.account.CreditCardAccount;
import com.ironhack.midterm.repository.CreditCardAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreditCardAccountController implements ICreditCardAccountController {

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @GetMapping("/creditCardAccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCardAccount> getCreditCardAccounts(){

        return creditCardAccountRepository.findAll();
    }
}
