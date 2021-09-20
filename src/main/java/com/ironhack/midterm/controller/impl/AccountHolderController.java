package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IAccountHolderController;
import com.ironhack.midterm.dao.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @GetMapping("/accountHolders")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAccountHolders(){

        return accountHolderRepository.findAll();
    }
}
