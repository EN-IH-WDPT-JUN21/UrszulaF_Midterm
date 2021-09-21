package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IAccountController;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts(){

        return accountRepository.findAll();
    }
}
