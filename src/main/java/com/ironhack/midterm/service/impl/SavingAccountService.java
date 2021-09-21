package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.account.SavingAccount;
import com.ironhack.midterm.repository.SavingAccountRepository;
import com.ironhack.midterm.service.interfaces.ISavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SavingAccountService implements ISavingAccountService {
    @Autowired
    SavingAccountRepository savingAccountRepository;

    public void update(Long id, SavingAccount savingAccount){
        Optional<SavingAccount> storedSavingAccount = savingAccountRepository.findById(id);
        if(storedSavingAccount.isPresent()){
            savingAccount.setId(storedSavingAccount.get().getId());
            savingAccountRepository.save(savingAccount);
        }
    }
}
