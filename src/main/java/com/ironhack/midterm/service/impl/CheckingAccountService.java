package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.account.CheckingAccount;
import com.ironhack.midterm.repository.CheckingAccountRepository;
import com.ironhack.midterm.service.interfaces.ICheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckingAccountService implements ICheckingAccountService {
    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    public void update(Long id, CheckingAccount checkingAccount){
        Optional<CheckingAccount> storedCheckingAccount = checkingAccountRepository.findById(id);
        if(storedCheckingAccount.isPresent()){
            checkingAccount.setId(storedCheckingAccount.get().getId());
            checkingAccountRepository.save(checkingAccount);
        }
    }
}
