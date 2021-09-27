package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.ICheckingAccountController;
import com.ironhack.midterm.dao.account.CheckingAccount;
import com.ironhack.midterm.dao.account.SavingAccount;
import com.ironhack.midterm.repository.CheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CheckingAccountController implements ICheckingAccountController {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @GetMapping("/checking-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getCheckingAccounts(){

        return checkingAccountRepository.findAll();
    }

    @PostMapping("/checking-accounts/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckingAccount store(@RequestBody @Valid CheckingAccount checkingAccount) {
        return checkingAccountRepository.save(checkingAccount);
    }
}
