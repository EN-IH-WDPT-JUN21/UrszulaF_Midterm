package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.controller.dto.SavingAccountDTO;
import com.ironhack.midterm.dao.account.SavingAccount;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.SavingAccountRepository;
import com.ironhack.midterm.service.interfaces.ISavingAccountService;
import com.ironhack.midterm.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavingAccountService implements ISavingAccountService {
    @Autowired
    SavingAccountRepository savingAccountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public void update(Long id, SavingAccount savingAccount){
        Optional<SavingAccount> storedSavingAccount = savingAccountRepository.findById(id);
        if(storedSavingAccount.isPresent()){
            savingAccount.setId(storedSavingAccount.get().getId());
            savingAccountRepository.save(savingAccount);
        }
    }
}
