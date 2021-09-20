package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IAccountController;
import com.ironhack.midterm.controller.interfaces.IStudentCheckingAccountController;
import com.ironhack.midterm.dao.Account;
import com.ironhack.midterm.dao.StudentCheckingAccount;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.repository.StudentCheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentCheckingAccountController implements IStudentCheckingAccountController {

    @Autowired
    private StudentCheckingAccountRepository studentCheckingAccountRepository;

    @GetMapping("/studentCheckingAccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCheckingAccount> getStudentCheckingAccounts(){

        return studentCheckingAccountRepository.findAll();
    }
}
