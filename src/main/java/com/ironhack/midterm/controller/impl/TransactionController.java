package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.TransactionDTO;
import com.ironhack.midterm.controller.interfaces.ITransactionController;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.account.Transaction;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.service.interfaces.IAccountService;
import com.ironhack.midterm.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController implements ITransactionController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    SavingAccountRepository savingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactions(){

        return transactionRepository.findAll();
    }

    @GetMapping("/transactions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transaction getById(@PathVariable(name="id") long transactionId){
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        return optionalTransaction.isPresent()? optionalTransaction.get() : null;
    }

    //    to access own transaction by transaction holder
    @GetMapping("/my-transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactions(@PathVariable(name="id") long id){
        return transactionService.getMyTransactions(id);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public void transfer(@RequestBody @Valid TransactionDTO transactionDTO){


        transactionService.transfer(transactionDTO.getSenderAccountId(), transactionDTO.getRecipientAccountId(), transactionDTO);
    }

}
