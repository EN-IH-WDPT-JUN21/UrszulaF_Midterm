package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.StudentCheckingAccount;

import java.util.List;

public interface IStudentCheckingAccountController {
    List<StudentCheckingAccount> getStudentCheckingAccounts();
}
