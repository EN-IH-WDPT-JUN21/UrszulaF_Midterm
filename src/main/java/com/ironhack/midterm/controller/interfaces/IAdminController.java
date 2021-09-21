package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.user.Admin;

import java.util.List;

public interface IAdminController {
    List<Admin> getAdmins();
}
