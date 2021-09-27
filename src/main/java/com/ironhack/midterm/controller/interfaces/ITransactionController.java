package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.account.Transaction;

import java.util.List;

public interface ITransactionController {
    List<Transaction> getTransactions();
}
