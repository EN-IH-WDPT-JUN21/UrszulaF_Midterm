package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountHolderService implements IAccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    public void update(Long id, AccountHolder accountHolder){
        Optional<AccountHolder> storedAccountHolder = accountHolderRepository.findById(id);
        if(storedAccountHolder.isPresent()){
            accountHolder.setId(storedAccountHolder.get().getId());
            accountHolderRepository.save(accountHolder);
        }
    }
}
