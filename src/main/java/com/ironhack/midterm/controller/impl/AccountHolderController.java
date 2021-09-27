package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IAccountHolderController;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.service.interfaces.IUserRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private IUserRetrieveService userRetrieveService;

    @GetMapping("/accountHolders")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAccountHolders(){

        return accountHolderRepository.findAll();
    }

    @GetMapping("/accountHolders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder retrieveUser(@PathVariable(name="id") long id){
        return userRetrieveService.retrieveUser(id);
    }
}
