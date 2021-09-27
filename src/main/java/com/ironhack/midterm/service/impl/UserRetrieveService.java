package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.service.interfaces.IUserRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserRetrieveService implements IUserRetrieveService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    public AccountHolder retrieveUser(Long id) {
        if (!accountHolderRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account with id " + id + " doesn't in the database");
        } else {
            return accountHolderRepository.findById(id).get();
        }
    }
}
