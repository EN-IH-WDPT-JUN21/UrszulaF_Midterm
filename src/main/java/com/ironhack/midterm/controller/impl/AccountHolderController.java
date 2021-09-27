package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.controller.dto.SavingAccountDTO;
import com.ironhack.midterm.controller.interfaces.IAccountHolderController;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.service.interfaces.IAccountHolderService;
import com.ironhack.midterm.service.interfaces.IUserRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private IAccountHolderService accountHolderService;

    @Autowired
    private IUserRetrieveService userRetrieveService;

    @GetMapping("/account-holders")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAccountHolders(){

        return accountHolderRepository.findAll();
    }

    @GetMapping("/account-holders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder retrieveUser(@PathVariable(name="id") long id){
        return userRetrieveService.retrieveUser(id);
    }

//        only for admin
    @PostMapping("/account-holders/new")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder store(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return accountHolderService.store(accountHolderDTO);
    }
}
