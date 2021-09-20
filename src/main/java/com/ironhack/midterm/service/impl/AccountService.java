package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.Account;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.service.interfaces.IAccountService;
import com.ironhack.midterm.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository accountRepository;

    public void update(Long id, Account account){
        Optional<Account> storedAccount = accountRepository.findById(id);
        if(storedAccount.isPresent()){
            account.setId(storedAccount.get().getId());
            accountRepository.save(account);
        }
    }


    //method to check the balance of the account
    void checkBalance(Long id){
        Optional<Account> storedAccount = accountRepository.findById(id);
        if(storedAccount.isEmpty()){
            System.out.println("There is no account with this id");
        }else{
            Money balance = storedAccount.get().getBalance();
            System.out.println("Balance is: "+balance);
        }
    }
}
