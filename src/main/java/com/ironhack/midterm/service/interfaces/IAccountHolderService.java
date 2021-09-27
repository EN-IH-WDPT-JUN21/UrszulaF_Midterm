package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.dao.user.AccountHolder;

public interface IAccountHolderService {
    void update(Long id, AccountHolder accountHolder);
    AccountHolder store(AccountHolderDTO accountHolderDTO);
}
