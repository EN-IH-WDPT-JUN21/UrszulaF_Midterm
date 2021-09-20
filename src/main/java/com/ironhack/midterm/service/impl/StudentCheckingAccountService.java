package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.CheckingAccount;
import com.ironhack.midterm.dao.StudentCheckingAccount;
import com.ironhack.midterm.repository.CheckingAccountRepository;
import com.ironhack.midterm.repository.StudentCheckingAccountRepository;
import com.ironhack.midterm.service.interfaces.ICheckingAccountService;
import com.ironhack.midterm.service.interfaces.IStudentCheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentCheckingAccountService implements IStudentCheckingAccountService {
    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    public void update(Long id, StudentCheckingAccount studentCheckingAccount){
        Optional<StudentCheckingAccount> storedStudentCheckingAccount = studentCheckingAccountRepository.findById(id);
        if(storedStudentCheckingAccount.isPresent()){
            studentCheckingAccount.setId(storedStudentCheckingAccount.get().getId());
            studentCheckingAccountRepository.save(studentCheckingAccount);
        }
    }
}
