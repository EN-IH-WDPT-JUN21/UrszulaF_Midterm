package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.dao.account.StudentCheckingAccount;

public interface IStudentCheckingAccountService {
    void update(Long id, StudentCheckingAccount studentCheckingAccount);
}
