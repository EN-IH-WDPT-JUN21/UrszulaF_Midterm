package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService implements IAccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    public AccountHolder store(AccountHolderDTO accountHolderDTO) {
        List<AccountHolder> accountHolder = accountHolderRepository.findAll();
        AccountHolder newAccountHolder = null;
        newAccountHolder = new AccountHolder(accountHolderDTO.getUsername(), accountHolderDTO.getPassword(), accountHolderDTO.getRole(), LocalDate.parse(accountHolderDTO.getDateOfBirth()), accountHolderDTO.getPrimaryAddress(), accountHolderDTO.getMailingAddress());
        if (!accountHolder.contains(newAccountHolder)) {
            return accountHolderRepository.save(newAccountHolder);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This accountHolder already exists in the system.");
        }
    }

    public void update(Long id, AccountHolder accountHolder){
        Optional<AccountHolder> storedAccountHolder = accountHolderRepository.findById(id);
        if(storedAccountHolder.isPresent()){
            accountHolder.setId(storedAccountHolder.get().getId());
            accountHolderRepository.save(accountHolder);
        }
    }
}
