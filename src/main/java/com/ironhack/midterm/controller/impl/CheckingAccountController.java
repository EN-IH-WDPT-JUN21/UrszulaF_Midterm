package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IAccountController;
import com.ironhack.midterm.controller.interfaces.ICheckingAccountController;
import com.ironhack.midterm.dao.Account;
import com.ironhack.midterm.dao.CheckingAccount;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.repository.CheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckingAccountController implements ICheckingAccountController {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @GetMapping("/checkingAccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getCheckingAccounts(){

        return checkingAccountRepository.findAll();
    }
}
