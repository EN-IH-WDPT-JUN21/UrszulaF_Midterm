package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IStudentCheckingAccountController;
import com.ironhack.midterm.dao.account.CheckingAccount;
import com.ironhack.midterm.dao.account.StudentCheckingAccount;
import com.ironhack.midterm.repository.StudentCheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentCheckingAccountController implements IStudentCheckingAccountController {

    @Autowired
    private StudentCheckingAccountRepository studentCheckingAccountRepository;

    @GetMapping("/student-checking-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCheckingAccount> getStudentCheckingAccounts(){

        return studentCheckingAccountRepository.findAll();
    }

    @PostMapping("/student-checking-accounts/new")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentCheckingAccount store(@RequestBody @Valid StudentCheckingAccount studentCheckingAccount) {
        return studentCheckingAccountRepository.save(studentCheckingAccount);
    }
}
