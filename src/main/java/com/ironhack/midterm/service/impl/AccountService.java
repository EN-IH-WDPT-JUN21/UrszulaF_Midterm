package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.service.interfaces.IAccountService;
import com.ironhack.midterm.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
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
    public List<Account>  getMyPrimaryAccounts(Long id){
        List<Account> ownerAccounts = accountRepository.findByPrimaryOwnerId(id);
        if(ownerAccounts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user doesn't have primary accounts");
        }else{
            return ownerAccounts;
        }
    }

    public List<Account>  getMySecondaryAccounts(Long id){
        List<Account> ownerAccounts = accountRepository.findBySecondaryOwnersId(id);
        if(ownerAccounts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user doesn't have secondary accounts");
        }else{
            return ownerAccounts;
        }
    }

    public void updateBalance(Long id, Money balance){
        Optional<Account> storedAccount = accountRepository.findById(id);
        if(storedAccount.isPresent()){
            storedAccount.get().setBalance(balance);
            accountRepository.save(storedAccount.get());
        }
    }

    public void updateStatus(Long id, Status status){
        Optional<Account> storedAccount = accountRepository.findById(id);
        if(storedAccount.isPresent()){
            storedAccount.get().setStatus(status);
            accountRepository.save(storedAccount.get());
        }
    }
    
}
