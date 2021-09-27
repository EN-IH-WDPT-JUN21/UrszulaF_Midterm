package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.AccountDTO;
import com.ironhack.midterm.controller.dto.SavingAccountDTO;
import com.ironhack.midterm.controller.interfaces.ISavingAccountController;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.account.SavingAccount;
import com.ironhack.midterm.repository.SavingAccountRepository;
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

    @GetMapping("/savingAccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingAccount> getSavingAccounts(){

        return savingAccountRepository.findAll();
    }

    //    only for admin
//    @PostMapping("/savingAccounts")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Account store(@RequestBody @Valid SavingAccountDTO savingAccountDTO) throws ParseException {
//        return savingAccountRepository.save(savingAccountDTO);
//    }
}
