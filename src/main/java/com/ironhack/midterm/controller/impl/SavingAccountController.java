package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IAccountController;
import com.ironhack.midterm.controller.interfaces.ISavingAccountController;
import com.ironhack.midterm.dao.Account;
import com.ironhack.midterm.dao.SavingAccount;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.repository.SavingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SavingAccountController implements ISavingAccountController {

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @GetMapping("/savingAccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingAccount> getSavingAccounts(){

        return savingAccountRepository.findAll();
    }
}
