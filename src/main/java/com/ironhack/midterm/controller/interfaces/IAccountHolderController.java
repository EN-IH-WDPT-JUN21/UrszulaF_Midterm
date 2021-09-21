package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.user.AccountHolder;

import java.util.List;

public interface IAccountHolderController {
    List<AccountHolder> getAccountHolders();
}
