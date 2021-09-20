package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.Admin;
import com.ironhack.midterm.repository.AdminRepository;
import com.ironhack.midterm.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {
    @Autowired
    AdminRepository studentRepository;

    public void update(Long id, Admin student){
        Optional<Admin> storedAdmin = studentRepository.findById(id);
        if(storedAdmin.isPresent()){
            student.setId(storedAdmin.get().getId());
            studentRepository.save(student);
        }
    }
}
