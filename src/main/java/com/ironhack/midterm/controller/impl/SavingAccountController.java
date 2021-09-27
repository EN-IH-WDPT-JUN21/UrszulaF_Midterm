package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.AccountDTO;
import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.controller.dto.SavingAccountDTO;
import com.ironhack.midterm.controller.interfaces.ISavingAccountController;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.account.SavingAccount;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.repository.SavingAccountRepository;
import com.ironhack.midterm.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
public class SavingAccountController implements ISavingAccountController {

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Autowired
    private ISavingAccountController savingAccountController;

    @GetMapping("/saving-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingAccount> getSavingAccounts(){

        return savingAccountRepository.findAll();
    }


    @PostMapping("/saving-accounts/new")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingAccount store(@RequestBody @Valid SavingAccount savingAccount) {
        return savingAccountRepository.save(savingAccount);
    }

}
