package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.user.AccountHolder;

public interface IUserRetrieveService {
    AccountHolder retrieveUser(Long id);
}
