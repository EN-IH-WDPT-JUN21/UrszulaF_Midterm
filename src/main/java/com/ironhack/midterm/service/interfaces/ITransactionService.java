package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.controller.dto.TransactionDTO;
import com.ironhack.midterm.dao.account.Transaction;
import com.ironhack.midterm.utils.Money;

import java.util.List;

public interface ITransactionService {
    void deposit(Long id, Money amount);
    void withdraw(Long id, Money amount);
    void transfer(Long senderTransactionId, Long recipientTransactionId, TransactionDTO transactionDTO);
    List<Transaction> getMyTransactions(Long id);
}
