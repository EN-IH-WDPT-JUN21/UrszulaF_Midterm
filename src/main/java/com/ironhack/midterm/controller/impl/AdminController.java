package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IAdminController;
import com.ironhack.midterm.dao.Admin;
import com.ironhack.midterm.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController implements IAdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admins")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> getAdmins(){

        return adminRepository.findAll();
    }
}
